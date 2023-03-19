package ugeoverflowtest.security;

import fr.uge.jee.ugeoverflow.security.CustomUserDetails;
import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsTest {

    @Mock
    private User mockedUser;

    @Test
    public void GetAuthorities() {
        when(mockedUser.getRole()).thenReturn(Role.ADMIN);
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority(Role.ADMIN.name()), authorities.iterator().next());
    }

    @Test
    public void GetPassword() {
        String password = "password";
        when(mockedUser.getPassword()).thenReturn(password);
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertEquals(password, customUserDetails.getPassword());
    }

    @Test
    public void GetUsername() {
        String username = "username";
        when(mockedUser.getUsername()).thenReturn(username);
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertEquals(username, customUserDetails.getUsername());
    }

    @Test
    public void IsAccountNonExpired() {
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertFalse(customUserDetails.isAccountNonExpired());
    }

    @Test
    public void IsAccountNonLocked() {
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertFalse(customUserDetails.isAccountNonLocked());
    }

    @Test
    public void IsCredentialsNonExpired() {
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertFalse(customUserDetails.isCredentialsNonExpired());
    }

    @Test
    public void IsEnabled() {
        CustomUserDetails customUserDetails = new CustomUserDetails(mockedUser);
        assertFalse(customUserDetails.isEnabled());
    }
}
