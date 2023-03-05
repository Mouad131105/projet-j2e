package fr.uge.jee.ugeoverflow.converter;

import fr.uge.jee.ugeoverflow.entities.Role;
import fr.uge.jee.ugeoverflow.entities.User;
import org.springframework.core.convert.converter.Converter;


public class StringToUserConverter implements Converter<String, User> {

    @Override
    public User convert(String s) {
        String[] data = s.split(",=");
        User newUser = new User();
        newUser.setUsername(data[0]);
        newUser.setPassword(data[1]);
        newUser.setEmail(data[2]);

        if (data[3].equals("AUTHENTIFIED")) {
            newUser.setRole(Role.AUTHENTIFIED);
        } else {
            throw new NullPointerException();
        }

        //followed Users
        //confidence score
        return newUser;
    }
}
