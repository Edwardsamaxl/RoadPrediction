package org.example.backend.Service;

import jakarta.servlet.http.HttpSession;
import org.example.backend.Entity.User;

public interface UserService {
    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果消息
     */
    int register(User user);

    /**
     * 登录
     *
     * @param username     用户名
     * @param password     密码
     * @return 登录结果消息
     */
    User login(String username, String password);

    /**
     * 更新密码
     *
     * @param username      用户ID
     * @param oldPassword 新密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    boolean updatePassword(String username, String oldPassword, String newPassword);
}
