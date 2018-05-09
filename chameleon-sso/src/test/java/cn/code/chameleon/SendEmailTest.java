package cn.code.chameleon;

import cn.code.chameleon.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;

/**
 * @author liumingyu
 * @create 2018-05-09 下午1:10
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SendEmailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @Test
    public void sendAttachmentsMail() throws Exception {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo("houshiduo@gmail.com");
        helper.setFrom("532033837@qq.com");
        helper.setSubject("Chameleon Verify Code");
        helper.setText("您好, 欢迎使用变色龙爬虫配置平台, 您的邮箱验证码是: 1f34, 请于120秒内输入验证, 请勿向他人泄露, 若非本人操作, 请忽略!");
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void sendEmail() throws Exception {
        userService.generateVerifyCode("houshiduo@gmail.com");
    }
}
