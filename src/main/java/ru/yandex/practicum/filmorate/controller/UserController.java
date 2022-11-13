package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.LoginValidatorUsingLogin;
import ru.yandex.practicum.filmorate.exception.DateValidationException;
import ru.yandex.practicum.filmorate.exception.LoginValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> findAll() {
        return users;
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        logger.info("UserController.createUser: Начали создание пользователя");
        if (LoginValidatorUsingLogin.isValidLogin(user.getLogin())){
            throw new LoginValidationException("UserController.createUser: Логин содержит пробелы");
        }
        if (user.getName().isEmpty()){
            logger.info("UserController.createUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        users.add(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        logger.info("UserController.updateUser: Обновляем пользователя");
        if (LoginValidatorUsingLogin.isValidLogin(user.getLogin())){
            throw new DateValidationException("UserController.updateUser: Логин содержит пробелы");
        }
        if (user.getName().isEmpty()){
            logger.info("UserController.updateUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        ListIterator<User> iterator = users.listIterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            if ((next.getId()).equals(user.getId())) {
                iterator.set(user);
            }
        }
    }
}
