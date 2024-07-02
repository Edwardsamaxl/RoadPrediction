package backend.Service;

public interface MailService {

    /**
     * 发送找回密码的邮件
     *
     * @param to      接收邮件的邮箱地址
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendPasswordResetEmail(String to, String subject, String content);
}
