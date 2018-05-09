package cn.code.chameleon.utils;

import cn.code.chameleon.dto.UserDTO;
import cn.code.chameleon.pojo.User;

/**
 * @author liumingyu
 * @create 2018-05-09 上午10:53
 */
public class ConvertUtil {

    /**
     * DTO转User
     *
     * @param userDTO
     * @return
     */
    public static User convertDTO2User(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setStatus(userDTO.getStatus());
        user.setCreateTime(userDTO.getCreateTime());
        user.setUpdateTime(userDTO.getUpdateTime());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setIsDelete(userDTO.getIsDelete());
        return user;
    }

    /**
     * User转DTO
     *
     * @param user
     * @return
     */
    public static UserDTO converUser2DTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setUpdateTime(user.getUpdateTime());
        userDTO.setIsDelete(user.getIsDelete());
        userDTO.setStatus(user.getStatus());
        userDTO.setCreateTime(user.getCreateTime());
        return userDTO;
    }
}
