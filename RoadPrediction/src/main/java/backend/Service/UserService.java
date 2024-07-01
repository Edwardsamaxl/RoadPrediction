package backend.Service;

import backend.Entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    public User registerUser(String username, String password) {
        User newUser = new User(nextId, username, password);
        users.add(newUser);
        nextId++;
        return newUser;
    }

    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public Optional<User> getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    // 可以添加更多的业务方法，如更新用户信息、删除用户等

}
