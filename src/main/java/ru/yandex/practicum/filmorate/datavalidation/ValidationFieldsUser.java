package ru.yandex.practicum.filmorate.datavalidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.exception.LoginValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class ValidationFieldsUser {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static boolean isValidLogin(String str) {
        logger.info("ValidationFieldsUser.isValidLogin: Проверяем на отсутствие пробелов в поле Login");
        Pattern pattern = Pattern.compile("\\s");
        return pattern.matcher(str).find();
    }
    public static void noFoundUser(User user, Map<Integer,User> users) {
        if (!users.containsKey(user.getId())) {
            logger.info("ValidationFieldsUser.noFoundUser: Проверяем на существование пользователя");
            throw new IdValidationException("Такого пользователя не существует");
        }
    }
    public static void validateFields(User user) {
        if (isValidLogin(user.getLogin())){
            logger.info("ValidationFieldsUser.validateFields: Проверяем на существование пользователя");
            throw new LoginValidationException("Логин содержит пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()){
            logger.info("ValidationFieldsUser.validateFields: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
    }
}
