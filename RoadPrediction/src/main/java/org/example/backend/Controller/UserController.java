package org.example.backend.Controller;

import org.example.backend.Entity.User;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.UserService;
import org.example.backend.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Hello world");
    }

    @PostMapping("/test")
    public void test(String name) {
        System.out.println(name);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<User>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.login(username, password);
        if (user != null) {
            return ResponseEntity.ok(Response.success(user));
        } else {
            return ResponseEntity.status(404).body(Response.error(404, "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody Map<String, String> regRequest) {
        String username = regRequest.get("username");
        String password = regRequest.get("password");
        String mail = regRequest.get("mail");

        User regUser = new User();
        regUser.setUsername(username);
        regUser.setPassword(password);
        regUser.setMail(mail);

        int status = userService.register(regUser);
        if (status == 200){
            User user = userMapper.selectByUsername(username);
            return ResponseEntity.ok(Response.success(user));
        }
        return ResponseEntity.status(403).body(Response.error(403, "Register failed"));
    }

    @PostMapping("/mail-sent")
    public ResponseEntity<Response<String>> findBackPassword(@RequestBody Map<String, String> findRequest){
        String username = findRequest.get("username");
        String mail = findRequest.get("mail");

        boolean emailSent = userService.findBackPassword(username, mail);

        if (emailSent) {
            return ResponseEntity.ok(Response.success("Password reset email sent successfully"));
        } else {
            return ResponseEntity.status(404).body(Response.error(404, "User not found or email does not match"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Response<String>> resetPassword(@RequestBody Map<String, String> request){
        String mail = request.get("mail");
        String code = request.get("code");
        String newPassword = request.get("newpassword");

        boolean passwordReset = userService.verifyCodeAndResetPassword(mail, code, newPassword);

        if(passwordReset){
            return ResponseEntity.ok(Response.success("Password reset successfully"));
        }else {
            return ResponseEntity.status(400).body(Response.error(400, "Invalid verification code or email"));
        }
    }
}
