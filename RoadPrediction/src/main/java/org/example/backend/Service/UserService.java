package org.example.backend.Service;

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
     * 找回密码
     *
     * @param username     用户名
     * @param mail     邮箱
     * @return 邮件发送成功信息
     */
    boolean findBackPassword(String username, String mail);

    /**
     * 根据验证码修改密码
     *
     * @param mail 邮箱
     * @param code 验证码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean verifyCodeAndResetPassword(String mail, String code, String newPassword);
}
