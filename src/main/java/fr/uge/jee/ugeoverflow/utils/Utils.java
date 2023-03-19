package fr.uge.jee.ugeoverflow.utils;

import java.util.regex.Pattern;

public final class Utils {
    public static final String USERNAME_REGEX = "(^[A-Za-z][\\w]{4,29}$)|(admin)";
    public static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+";
    public static final String PASSWORD_MESSAGE = "The password must contain at least eight characters, such that at least : one uppercase, one lowercase, one special character, one digit.";

    public static boolean isValidPassword(String password) {
        return Pattern.matches(password, PASSWORD_REGEX);
    }
}
