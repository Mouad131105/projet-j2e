package fr.uge.jee.ugeoverflow.converter;

import fr.uge.jee.ugeoverflow.entities.Role;
import org.springframework.core.convert.converter.Converter;

public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String s) {
        switch (s) {
            case "AUTHENTIFIED":
                return Role.AUTHENTIFIED;
            case "ADMIN":
                return Role.ADMIN;
            case "GUEST":
                return Role.GUEST;
            default:
                throw new IllegalArgumentException("Invalid value for Role: " + s);
        }
    }
}
