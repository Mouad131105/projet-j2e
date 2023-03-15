package fr.uge.jee.ugeoverflow.converter;

import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.core.convert.converter.Converter;

import java.util.*;

public class StringToSetOfUserConverter implements Converter<String, Set<User>> {

    StringToUserConverter stringToUserConverter = new StringToUserConverter();

    @Override
    public Set<User> convert(String s) {
        List<String> data = Arrays.asList(s.split(","));
        Set<User> res = new HashSet<>();
        data.forEach(x ->
                res.add(stringToUserConverter.convert(x))
        );
        return res;
    }
}
