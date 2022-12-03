package ru.yandex.practicum.filmorate.datavalidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;


/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class ValidationFieldsUser {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static void noFoundUser(User user, Map<Integer, User> users) {
        if (!users.containsKey(user.getId())) {
            logger.info("ValidationFieldsUser.noFoundUser: Проверяем на существование пользователя");
            throw new NotFoundException("Такого пользователя не существует");
        }
    }
}
