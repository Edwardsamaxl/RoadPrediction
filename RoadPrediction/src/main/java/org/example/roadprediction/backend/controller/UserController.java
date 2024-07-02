package org.example.roadprediction.backend.controller;

import org.example.roadprediction.backend.Entity.User;
import org.example.roadprediction.backend.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/test")
    public void test(int id) {
        System.out.println(id);
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        System.out.println(user.getId());
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        System.out.println("username = " + username + ", password = " + password);
        User user = userService.login(username, password);
        return user != null ? "登录成功" : "用户名或密码错误";
    }
}
