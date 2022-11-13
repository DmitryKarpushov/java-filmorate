package ru.yandex.practicum.filmorate.datavalidation;

import java.util.regex.Pattern;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class LoginValidatorUsingLogin {
    public static boolean isValidLogin(String str) {
        Pattern pattern = Pattern.compile("\\s");
        return pattern.matcher(str).find();
    }
}
