package ugeoverflowtest.user;

import fr.uge.jee.ugeoverflow.authentication.AuthenticationService;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserController;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(userController).build();
    }

    @Test
    public void testProcessFollow() {
        String userFollow = "Alice";
        User user = new User();
        user.setUsername(userFollow);

        User currentUser = new User();
        currentUser.setUsername("Bob");
        when(authenticationService.getLoggedUser()).thenReturn(currentUser);

        Set<User> followedUsers = new HashSet<User>();
        followedUsers.add(user);
        when(userService.findAllFollowedUsersFromUser(currentUser.getUsername())).thenReturn(followedUsers);

        when(userService.findUserByUsername(userFollow)).thenReturn(user);

        Model model = new ExtendedModelMap();
        String result = userController.processFollow(userFollow, model);
        assertEquals("user-profile", result);
        assertFalse(model.containsAttribute("selectedUserError"));
        assertTrue(model.containsAttribute("user"));
        assertTrue(model.containsAttribute("loggedUser"));
        assertTrue(model.containsAttribute("questions"));
        assertTrue(model.containsAttribute("isFollow"));
        assertFalse((boolean)model.asMap().get("isFollow"));
    }

    @Test
    public void getProfileTest() throws Exception {
        // Given
        User user = new User();
        user.setUsername("testuser");
        when(userService.findUserByUsername(anyString())).thenReturn(user);
        when(authenticationService.getLoggedUser()).thenReturn(user);

        // When
        mockMvc.perform(get("/users/profile/{username}", "testuser"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("loggedUser", user))
                .andExpect(model().attribute("isFollow", false))
                .andExpect(model().attribute("selectedUserError", null))
                .andExpect(model().attribute("questions", Collections.emptyList()));
    }
}
