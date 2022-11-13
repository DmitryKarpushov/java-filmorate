package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.ValidationFieldsUser;
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
        ValidationFieldsUser.validateFields(user);
        int idUser = generateId();
        user.setId(idUser);
        users.put(idUser,user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        logger.info("UserController.updateUser: Обновляем пользователя");
        ValidationFieldsUser.noFoundUser(user,users);
        ValidationFieldsUser.validateFields(user);
        users.put(user.getId(),user);
        return user;
    }
}
