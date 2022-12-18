package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.datavalidation.ValidationFieldsUser;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Service
@Slf4j
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }


    public Map<Integer, User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User addUser(User user) {
        log.info("UserService.addUser: Начали создание пользователя");
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("UserController.createUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        return inMemoryUserStorage.addUser(user);
    }

    public void deleteUser(Integer id) {
        inMemoryUserStorage.deleteUser(id);
    }

    public User updateUser(User user) {
        log.info("UserService.updateUser: Обновляем пользователя");
        ValidationFieldsUser.noFoundUser(user, inMemoryUserStorage.getUsers());
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("UserController.updateUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        return inMemoryUserStorage.updateUser(user);
    }

    public List<User> getUsersList() {
        return new ArrayList<>(inMemoryUserStorage.getUsers().values());
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        if (idUser < 1 || idFriend < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        inMemoryUserStorage.getUsers().get(idUser).addFriend(idFriend);
        inMemoryUserStorage.getUsers().get(idFriend).addFriend(idUser);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        if (idUser < 1 || idFriend < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        inMemoryUserStorage.getUsers().get(idUser).deleteFriend(idFriend);
        inMemoryUserStorage.getUsers().get(idFriend).deleteFriend(idUser);
    }

    /**
     * 1)Выводим друзей Юзера
     */
    public List<User> getUserFriends(Integer idUser) {
        if (idUser < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        List<User> friends = new ArrayList<>();
        for (Integer friendId : inMemoryUserStorage.getUsers().get(idUser).getFriends()) {
            if (inMemoryUserStorage.getUsers().containsKey(friendId)) {
                friends.add(inMemoryUserStorage.getUsers().get(friendId));
            }
        }
        return friends;
    }

    /**
     * 1)Выводим общих друзей
     */
    public List<User> getCommonFriend(Integer idUser, Integer idFriend) {
        if (idUser < 1 || idFriend < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        List<User> commonFriend = new ArrayList<>();
        for (Integer idFriendUser : inMemoryUserStorage.getUsers().get(idUser).getFriends()) {
            if (inMemoryUserStorage.getUsers().get(idFriend).getFriends().contains(idFriendUser)) {
                commonFriend.add(inMemoryUserStorage.getUsers().get(idFriendUser));
            }
        }
        return commonFriend;
    }
}
