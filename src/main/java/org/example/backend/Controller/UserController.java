package org.example.backend.Controller;

import org.example.backend.Entity.User;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.UserService;
import org.example.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.example.backend.utils.PasswordUpdateUtil;


import java.util.Map;


@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public void hello(){
        System.out.println("helloworld");
    }

    @PostMapping("/test")
    public void test(String name) {
        System.out.println(name);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")//跳转前端完成，实现后端业务
    public ResponseEntity<Response<User>> login(@RequestBody User loginUser) {

        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user != null) {
            return ResponseEntity.ok(Response.success(user));
        } else {
            return ResponseEntity.status(404).body(Response.error(404, "Invalid username or password"));
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody User regUser) {
        System.out.println("111");
        int status = userService.register(regUser);
        if (status == 200){
            User user = userMapper.selectByUsername(regUser.getUsername());
            return ResponseEntity.ok(Response.success(user));
        }
        return ResponseEntity.status(404).body(Response.error(404, "register failed"));
    }

    @PostMapping("/update")
    public ResponseEntity<Response<String>> updatePassword(@RequestBody PasswordUpdateUtil passwordUpdateUtil) {
        boolean updated = userService.updatePassword(
                passwordUpdateUtil.getUsername(),
                passwordUpdateUtil.getOldPassword(),
                passwordUpdateUtil.getNewPassword());

        if (updated) {
            return ResponseEntity.ok(Response.success("密码更新成功"));
        } else {
            return ResponseEntity.status(404).body(Response.error(404, "密码更新失败"));
        }
    }

}