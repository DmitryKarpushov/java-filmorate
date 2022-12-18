package ru.yandex.practicum.filmorate.datavalidation;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
@Slf4j
public class ValidationFieldsUser {

    public static void noFoundUser(User user, Map<Integer, User> users) {
        if (!users.containsKey(user.getId())) {
            log.info("ValidationFieldsUser.noFoundUser: Проверяем на существование пользователя");
            throw new UserNotFoundException("Такого пользователя не существует");
        }
    }
}
