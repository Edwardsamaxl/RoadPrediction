package backend.Service;

import jakarta.servlet.http.HttpSession;
import backend.Entity.User;

public interface UserService {
    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    int register(User user);

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    User login(String loginName, String passwordMD5);
}
