package cn.code.chameleon.service.impl;

import cn.code.chameleon.enums.ResultCodeEnum;
import cn.code.chameleon.enums.UserStatusEnum;
import cn.code.chameleon.exception.ChameleonException;
import cn.code.chameleon.mapper.UserMapper;
import cn.code.chameleon.pojo.User;
import cn.code.chameleon.pojo.UserExample;
import cn.code.chameleon.service.RedisClient;
import cn.code.chameleon.service.UserService;
import cn.code.chameleon.utils.Constants;
import cn.code.chameleon.utils.ConvertUtil;
import cn.code.chameleon.utils.CookieUtil;
import cn.code.chameleon.utils.EncryptUtil;
import cn.code.chameleon.utils.JsonUtils;
import cn.code.chameleon.utils.RandomUtil;
import cn.code.chameleon.utils.RegexUtils;
import cn.code.chameleon.utils.UserContext;
import cn.code.chameleon.utils.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author liumingyu
 * @create 2018-05-08 下午6:45
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    /**
     * 检查账号是否可用 true:可用;false:不可用
     *
     * @param account
     * @return
     * @throws ChameleonException
     */
    @Override
    public boolean checkAccount(String account) throws ChameleonException {
        if (account == null || "".equals(account)) {
            throw new ChameleonException(ResultCodeEnum.USER_ACCOUNT_PATTERN_ERROR);
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(account);
        criteria.andIsDeleteEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        return !ValidateUtil.isValid(users);
    }

    /**
     * 判断验证码是否正确 true:有效; false:无效
     *
     * @param account
     * @param code
     * @return
     * @throws ChameleonException
     */
    @Override
    public boolean checkVerifyCode(String account, String code) throws ChameleonException {
        if (code == null || "".equals(code)) {
            throw new ChameleonException(ResultCodeEnum.VERIFY_CODE_VALIDATE_FAILED);
        }
        String verifyCode = redisClient.get(account);
        code = code.toLowerCase();
        //降级
        if (verifyCode == null || "".equals(verifyCode)) {
            return true;
        }
        if (code.equals(verifyCode)) {
            return true;
        }
        return false;
    }

    /**
     * 注册用户
     *
     * @param user
     * @param code
     * @throws ChameleonException
     */
    @Override
    public void saveUser(User user, String code) throws ChameleonException {
        if (user == null) {
            throw new ChameleonException(ResultCodeEnum.USER_DATA_EMPTY);
        }
        if (!RegexUtils.checkEmail(user.getEmail())) {
            throw new ChameleonException(ResultCodeEnum.USER_ACCOUNT_PATTERN_ERROR);
        }
        if (!checkAccount(user.getEmail())) {
            throw new ChameleonException(ResultCodeEnum.USER_ACCOUNT_HAS_EXISTED);
        }
        if (user.getName() == null || "".equals(user.getName())) {
            throw new ChameleonException(ResultCodeEnum.USER_NAME_CAN_NOT_EMPTY);
        }
        if (user.getPassword() == null || "".equals(user.getPassword()) || user.getPassword().length() < 6 || user.getPassword().length() > 18) {
            throw new ChameleonException(ResultCodeEnum.USER_PASSWORD_LEN_ERROR);
        }
        if (!checkVerifyCode(user.getEmail(), code)) {
            throw new ChameleonException(ResultCodeEnum.VERIFY_CODE_VALIDATE_FAILED);
        }
        String password = EncryptUtil.md5Digest(user.getPassword());
        if (password == null || "".equals(password)) {
            throw new ChameleonException(ResultCodeEnum.USER_PASSWORD_MD5_FAILED);
        }
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(false);
        user.setStatus(UserStatusEnum.OFFLINE.getCode());
        userMapper.insert(user);
    }

    /**
     * 生成验证码
     *
     * @param account
     * @throws ChameleonException
     */
    @Override
    public void generateVerifyCode(String account) throws ChameleonException {
        String code = RandomUtil.generateVerifyCode(new Random().nextInt(2) + 4).toLowerCase();
        String temp = redisClient.get(Constants.TIME_INTERVAL);
        long seconds = Constants.TIME_INTERVAL_SECONDS;
        if (temp != null && !"".equals(temp) && RegexUtils.checkDigit(temp)) {
            seconds = Long.valueOf(temp);
        }
        //设置验证码缓存
        redisClient.set(account, code, seconds, TimeUnit.SECONDS);
        try {
            //EmailUtil.sendTextMail(account, code, seconds);
            this.sendMail(account, code, seconds);
        } catch (Exception e) {
            LOGGER.error("Server send email to {} failed: {}", account, e);
            throw new ChameleonException(ResultCodeEnum.SEND_EMAIL_FAILED);
        }
    }

    @Async
    public void sendMail(String account, String code, long seconds) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(account);
        helper.setFrom(username);
        helper.setSubject("Chameleon Verify Code");
        helper.setText("您好, 欢迎使用变色龙爬虫配置平台, 您的验证码是: " + code + ", 请于" + seconds + "秒内输入验证, 请勿向他人泄露, 若非本人操作, 请忽略!");
        javaMailSender.send(mimeMessage);
    }

    /**
     * 忘记密码之后的更改密码操作
     *
     * @param account
     * @param password
     * @return
     * @throws ChameleonException
     */
    @Override
    public void updatePassword(String account, String password, String code) throws ChameleonException {
        if (!checkVerifyCode(account, code)) {
            throw new ChameleonException(ResultCodeEnum.VERIFY_CODE_VALIDATE_FAILED);
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(account);
        criteria.andIsDeleteEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        if (!ValidateUtil.isValid(users)) {
            throw new ChameleonException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        User user = users.get(0);
        user.setPassword(EncryptUtil.md5Digest(password));
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param request
     * @param response
     * @return
     * @throws ChameleonException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, ChameleonException.class})
    @Override
    public String login(String account, String password, HttpServletRequest request, HttpServletResponse response) throws ChameleonException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(account);
        criteria.andPasswordEqualTo(EncryptUtil.md5Digest(password));
        List<User> users = userMapper.selectByExample(example);
        if (!ValidateUtil.isValid(users)) {
            throw new ChameleonException(ResultCodeEnum.USER_ACCOUNT_PASSWORD_ERROR);
        }
        User user = users.get(0);
        user.setStatus(UserStatusEnum.ONLINE.getCode());
        userMapper.updateByPrimaryKeySelective(user);

        String token = UUID.randomUUID().toString();
        user.setPassword(null);

        redisClient.set(Constants.USER_TOKEN_KEY + ":" + token,
                JsonUtils.toJsonStrWithEmptyDefault(user),
                Constants.SSO_TOKEN_EXPIRE,
                TimeUnit.SECONDS);
        CookieUtil.setCookie(request, response, Constants.TOKEN_COOKIE, token);

        UserContext.setCurrentUser(ConvertUtil.converUser2DTO(user));
        return token;
    }

}
