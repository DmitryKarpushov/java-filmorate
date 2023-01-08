package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDb;
import ru.yandex.practicum.filmorate.dao.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;


/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Service
public class UserService {

    final UserDb userDbStorage;

    @Autowired
    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

    public User add(User user) {
        user.setId(userDbStorage.add(user));
        return user;
    }

    public void update(User user) {
        getById(user.getId());
        userDbStorage.update(user);
    }

    public List<User> getAll() {
        return userDbStorage.findAll();
    }

    public boolean addFriend(Integer idUser, Integer idFriend) {
        getById(idUser);
        getById(idFriend);
        return userDbStorage.addRequestsFriendship(idUser, idFriend);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        getById(idUser);
        getById(idFriend);
        if (!userDbStorage.deleteFriends(idUser, idFriend)) {
            throw new NotFoundException("Не удалось удалить пользователя из друзей");
        }
    }

    public List<User> getUserFriends(Integer idUser) {
        getById(idUser);
        List<Integer> idFriends = userDbStorage.findAllFriends(idUser);
        List<User> friends = new ArrayList<>();
        for (Integer friendId : idFriends) {
            friends.add(getById(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriend(Integer idUser, Integer idFriend) {
        getById(idUser);
        getById(idFriend);
        List<User> commonFriend = new ArrayList<>();
        Set<Integer> common = new HashSet<>(userDbStorage.findAllFriends(idUser));
        common.retainAll(userDbStorage.findAllFriends(idFriend));
        for (Integer idFriendUser : common) {
            commonFriend.add(getById(idFriendUser));
        }
        return commonFriend;
    }

    public User getById(Integer id) {
        if (id < 1000 && id > 0) {
            return userDbStorage.findById(id);
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
    }
}
