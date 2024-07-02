package backend.Controller;

import backend.Entity.*;
import backend.Service.UserService;
import backend.Mapper.UserMapper;
import backend.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")//跳转前端完成，实现后端业务
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
        int status = userService.register(regUser);
        if (status == 200){
            User user = userMapper.selectByUsername(regUser.getUsername());
            return ResponseEntity.ok(Response.success(user));
        }
        return ResponseEntity.status(404).body(Response.error(404, "register failed"));
    }
}