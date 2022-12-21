package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;


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

    public List<User> getUsers() {
        return inMemoryUserStorage.getAll();
    }

    public User getUserById(Integer id) {
        return inMemoryUserStorage.getById(id);
    }

    public User addUser(User user) {
        log.info("UserService.addUser: Начали создание пользователя");
        return inMemoryUserStorage.add(user);
    }

    public void deleteUser(Integer id) {
        inMemoryUserStorage.delete(id);
    }

    public User updateUser(User user) {
        log.info("UserService.updateUser: Обновляем пользователя");
        return inMemoryUserStorage.update(user);
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.getById(idUser).addFriend(idFriend);
        inMemoryUserStorage.getById(idFriend).addFriend(idUser);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.getById(idUser).deleteFriend(idFriend);
        inMemoryUserStorage.getById(idFriend).deleteFriend(idUser);
    }

    /**
     * 1)Выводим друзей Юзера
     */
    public List<User> getUserFriends(Integer idUser) {
        List<User> friends = new ArrayList<>();
        for (Integer friendId : inMemoryUserStorage.getById(idUser).getFriends()) {
            friends.add(inMemoryUserStorage.getById(friendId));
        }
        return friends;
    }

    /**
     * 1)Выводим общих друзей
     */
    public List<User> getCommonFriend(Integer idUser, Integer idFriend) {
        List<User> commonFriend = new ArrayList<>();
        for (Integer idFriendUser : inMemoryUserStorage.getById(idUser).getFriends()) {
            if (inMemoryUserStorage.getById(idFriend).getFriends().contains(idFriendUser)) {
                commonFriend.add(inMemoryUserStorage.getById(idFriendUser));
            }
        }
        return commonFriend;
    }
}
