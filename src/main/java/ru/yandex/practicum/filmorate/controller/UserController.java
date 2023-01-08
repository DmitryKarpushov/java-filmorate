package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getUsers() {
        log.info("UserController. getUsers. Получение списка пользователей");
        return userService.getAll();
    }

    @PostMapping()
    public User createUser(@Valid @RequestBody User user) {
        log.info("UserController. createUser. Создание пользователя");
        return userService.add(user);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@Valid @RequestBody @NotNull User user) {
        log.info("UserController. updateUser. Обновление пользователя");
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        log.info("UserController. getUserById. Получение пользователя по id");
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Boolean> addUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("UserController. addUserFriend. Добавление в друзья");
        return new ResponseEntity<>(userService.addFriend(id, friendId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> deleteUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("UserController. deleteUserFriend. Удаление из друзей");
        userService.deleteFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getUserFriends(@PathVariable Integer id) {
        log.info("UserController. getUserFriends. Удаление из друзей");
        return new ResponseEntity<>(userService.getUserFriends(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonUsersFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("UserController. getCommonUsersFriends. Получение общих друзей.");
        return new ResponseEntity<>(userService.getCommonFriend(id, otherId), HttpStatus.OK);
    }
}
