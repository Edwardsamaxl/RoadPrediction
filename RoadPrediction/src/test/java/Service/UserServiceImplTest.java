package Service;

import org.example.roadprediction.backend.Entity.User;
import org.example.roadprediction.backend.Mapper.UserMapper;
import org.example.roadprediction.backend.Service.MailService;
import org.example.roadprediction.backend.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        User user = new User(1, "testuser", "password123", "test@mail.com");

        when(userMapper.selectByUsername("testuser")).thenReturn(null);
        when(userMapper.insert(user)).thenReturn(1);

        String result = userService.register(user);

        assertEquals("用户注册成功！", result);
        verify(mailService, times(1)).sendPasswordResetEmail(eq("test@mail.com"), anyString(), anyString());
    }

    @Test
    public void testLogin() {
        User user = new User(1, "testuser", "password123", "test@mail.com");

        when(userMapper.selectByUsernameAndPassword("testuser", "password123")).thenReturn(user);

        User result = userService.login("testuser", "password123");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    public void testVerifyPassword() {
        User user = new User(1, "testuser", "password123", "test@mail.com");

        when(userMapper.selectById(1)).thenReturn(user);

        boolean result = userService.verifyPassword(1, "password123");

        assertTrue(result);
    }

    @Test
    public void testUpdatePassword() {
        User user = new User(1, "testuser", "password123", "test@mail.com");

        when(userMapper.selectById(1)).thenReturn(user);
        when(userMapper.updatePassword(user)).thenReturn(1);

        boolean result = userService.updatePassword(1, "newpassword123");

        assertTrue(result);
        verify(userMapper, times(1)).updatePassword(user);
    }
}

