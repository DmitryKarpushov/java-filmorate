package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.LoginValidatorUsingLogin;
import ru.yandex.practicum.filmorate.exception.DateValidationException;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.exception.LoginValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private Map<Integer, User> users = new HashMap<>();
    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        logger.info("UserController.createUser: Начали создание пользователя");
        if (LoginValidatorUsingLogin.isValidLogin(user.getLogin())){
            throw new LoginValidationException("1)UserController.createUser: Логин содержит пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()){
            logger.info("UserController.createUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        int idUser = generateId();
        user.setId(idUser);
        users.put(idUser,user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        logger.info("UserController.updateUser: Обновляем пользователя");
        if (!users.containsKey(user.getId())){
            throw new IdValidationException("FilmController.createFilm: Такого фильма не существует");
        }
        if (LoginValidatorUsingLogin.isValidLogin(user.getLogin())){
            throw new DateValidationException("UserController.updateUser: Логин содержит пробелы");
        }
        if (user.getName().isEmpty()){
            logger.info("UserController.updateUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        users.put(user.getId(),user);
        return user;
    }
}
