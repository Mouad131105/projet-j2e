package ugeoverflowtest.user;

import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserRepository;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserByUsername() {
        User user = new User("Robert", "robert@gmail.com", "Azerty1234!", Role.AUTHENTIFIED, new HashSet<>());
        when(userRepository.findUserByUsername("Robert")).thenReturn(user);
        User foundUser = userService.findUserByUsername("Robert");
        verify(userRepository, times(1)).findUserByUsername("Robert");
        Assertions.assertEquals(user, foundUser);
    }

    @Test
    public void getAllUsers() {
        User user1 = new User("Louise", "louise@gmail.com", "Azerty1234!", Role.AUTHENTIFIED, new HashSet<>());
        User user2 = new User("Robert", "robert@gmail.com", "Azerty1234!", Role.AUTHENTIFIED, new HashSet<>());
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
        Assertions.assertEquals(users, allUsers);
    }

    @Test
    public void existsUsername() {
        when(userRepository.existsByUsername("Louise")).thenReturn(true);
        boolean exists = userService.existsUsername("Louise");
        verify(userRepository, times(1)).existsByUsername("Louise");
        Assertions.assertTrue(exists);
    }

    @Test
    public void getPassword() {
        when(userRepository.getUserByPassword("Louise")).thenReturn("Azerty1234!");
        String password = userService.getPassword("Louise");
        verify(userRepository, times(1)).getUserByPassword("Louise");
        Assertions.assertEquals("Azerty1234!", password);
    }
}
