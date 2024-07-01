package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    /**
     * 登录页面跳转
     * @return
     */
    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "user/login";
    }

    /**
     * 注册页面跳转
     * @return
     */
    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "user/register";
    }
}
