package org.example.backend.Service.Impl;

import org.example.backend.Entity.User;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public int register(User user) {
        // 检查用户名是否已存在
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return 403;//"用户名已存在，请选择其他用户名。";
        }

        // 插入新用户数据到数据库
        int rowsAffected = userMapper.insert(user);
        if (rowsAffected > 0) {
            // 发送邮件通知注册成功（示例，具体内容根据需求修改）
            String subject = "注册成功通知";
            String content = "尊敬的用户，您已成功注册。";

            return 200;//"用户注册成功！";
        } else {
            return 403;//"用户注册失败，请稍后重试。";
        }
    }

    public User login(String username, String password) {
        // 查询用户记录
        User user = userMapper.selectByUsernameAndPassword(username, password);
        // 如果用户存在且密码匹配，则返回用户对象，否则返回 null
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
    // 校验旧密码是否正确
    public boolean verifyPassword(Integer userId, String oldPassword) {
        User user = userMapper.selectById(userId);
        return user != null && user.getPassword().equals(oldPassword);
    }

    // 更新密码
    public boolean updatePassword(Integer userId, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            int rows = userMapper.updatePassword(user);
            return rows > 0;
        }
        return false;
    }
}
