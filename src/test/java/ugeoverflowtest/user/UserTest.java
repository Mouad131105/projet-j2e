package ugeoverflowtest.user;

import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    @Mock
    private User mockedUser;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void UsernameNotBlank() {
        User user = new User("", "Azerty1234!", "email@domain.com", Role.AUTHENTIFIED, new HashSet<>());
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void PasswordNotBlank() {
        User user = new User("user", "", "email@demain.com", Role.AUTHENTIFIED, new HashSet<>());
        assertFalse(validator.validate(user).isEmpty());
    }

    @Test
    public void EmailNotBlank() {
        User user = new User("user", "Azerty1234!", "", Role.AUTHENTIFIED, new HashSet<>());
        assertFalse(validator.validate(user).isEmpty());
    }
}