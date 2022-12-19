package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("UserController. findAll. Получаем всех пользователей.");
        return new ArrayList<>(userService.getUsers().values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("UserController. createUser. Создание пользователя.");
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("UserController. updateUser. Обновление пользователя.");
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        log.info("UserController. getUser. Получение пользователя.");
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> putUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("UserController. putUserFriend. Добавление в друзья.");
        userService.addFriend(id, friendId);
        return new ResponseEntity<>(userService.getUsers().get(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<User> deleteUserFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("UserController. deleteUserFriend. Удаление из друзей.");
        userService.deleteFriend(id, friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getUserFriends(@PathVariable Integer id) {
        log.info("UserController. getUserFriends. Получение друзей.");
        return new ResponseEntity<>(userService.getUserFriends(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonUsersFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("UserController. getCommonUsersFriends. Получение общих друзей.");
        return new ResponseEntity<>(userService.getCommonFriend(id, otherId), HttpStatus.OK);
    }
}
