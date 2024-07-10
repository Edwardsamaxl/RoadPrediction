package org.example.backend.Service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.backend.Entity.User;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.UserService;
import org.example.backend.Service.VerificationCodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationCodeManager verificationCodeManager;
    public int register(User user) {
        // 检查用户名是否已存在
        if (userMapper.selectByUsername(user.getUsername()) != null) {
            return 403;//"用户名已存在，请选择其他用户名。";
        }
        if(userMapper.selectByMail(user.getMail()) != null){
            return 402;//“邮箱已被占用，请更改邮箱。”
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // 插入新用户数据到数据库
        int rowsAffected = userMapper.insert(user);
        return rowsAffected > 0 ? 200 : 500;
    }

    public User login(String username, String password) {
        // 查询用户记录
        User user = userMapper.selectByUsername(username);

        // 如果用户存在且密码匹配，则返回用户对象，否则返回 null
        if (user != null && bCryptPasswordEncoder.matches(password,user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    //找回密码
    public boolean findBackPassword(String username, String mail){
        User user = userMapper.selectByUsernameAndMail(username, mail);
        if (user == null){
            return false;
        }
        String verificationCode = generateVerificationCode();
        verificationCodeManager.storeVerificationCode(user.getMail(), verificationCode);

        try{
            sendEmail(user.getMail(), "Password Reset Verification Code", "Your verification code is: " + verificationCode);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateVerificationCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public boolean verifyCodeAndResetPassword(String mail, String code, String newPassword){
        String storedCode = verificationCodeManager.getVerificationCode(mail);
        if(storedCode != null && storedCode.equals(code)){
            User user = userMapper.selectByMail(mail);
            if(user != null){
                user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                int row = userMapper.updatePassword(user);
                if(row > 0){
                    verificationCodeManager.removeVerificationCode(mail);
                    return true;
                }else return false;
            }
        }
        return false;
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        if (to == null || to.isEmpty()){
            throw new IllegalArgumentException("To address must not be null or empty");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("1377413081@qq.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
