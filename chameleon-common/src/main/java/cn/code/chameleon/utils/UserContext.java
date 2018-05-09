package cn.code.chameleon.utils;

import cn.code.chameleon.dto.UserDTO;

/**
 * @author liumingyu
 * @create 2018-05-09 上午10:47
 */
public class UserContext {

    private static ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();

    public static UserDTO getCurrentUser() {
        UserDTO userDTO = currentUser.get();
        if (userDTO == null) {
            return null;
        }
        return userDTO;
    }

    public static void setCurrentUser(UserDTO user) {
        currentUser.set(user);
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
