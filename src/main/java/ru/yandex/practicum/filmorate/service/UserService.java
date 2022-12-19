package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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
        return inMemoryUserStorage.getAll();
    }

    public User getUserById(Integer id) {
        existsUser(id);
        return inMemoryUserStorage.getById(id);
    }

    public User addUser(User user) {
        log.info("UserService.addUser: Начали создание пользователя");
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("UserController.createUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        return inMemoryUserStorage.add(user);
    }

    public void deleteUser(Integer id) {
        inMemoryUserStorage.delete(id);
    }

    public User updateUser(User user) {
        log.info("UserService.updateUser: Обновляем пользователя");
        existsUser(user);
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("UserController.updateUser: Устанавливаем Имя пользователю(его логин)");
            user.setName(user.getLogin());
        }
        return inMemoryUserStorage.update(user);
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        existsUser(idUser, idFriend);
        inMemoryUserStorage.getAll().get(idUser).addFriend(idFriend);
        inMemoryUserStorage.getAll().get(idFriend).addFriend(idUser);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        existsUser(idUser, idFriend);
        inMemoryUserStorage.getAll().get(idUser).deleteFriend(idFriend);
        inMemoryUserStorage.getAll().get(idFriend).deleteFriend(idUser);
    }

    /**
     * 1)Выводим друзей Юзера
     */
    public List<User> getUserFriends(Integer idUser) {
        existsUser(idUser);
        List<User> friends = new ArrayList<>();
        for (Integer friendId : inMemoryUserStorage.getAll().get(idUser).getFriends()) {
            if (inMemoryUserStorage.getAll().containsKey(friendId)) {
                friends.add(inMemoryUserStorage.getAll().get(friendId));
            }
        }
        return friends;
    }

    /**
     * 1)Выводим общих друзей
     */
    public List<User> getCommonFriend(Integer idUser, Integer idFriend) {
        existsUser(idUser, idFriend);
        List<User> commonFriend = new ArrayList<>();
        for (Integer idFriendUser : inMemoryUserStorage.getAll().get(idUser).getFriends()) {
            if (inMemoryUserStorage.getAll().get(idFriend).getFriends().contains(idFriendUser)) {
                commonFriend.add(inMemoryUserStorage.getAll().get(idFriendUser));
            }
        }
        return commonFriend;
    }

    private void existsUser(Integer id) {
        if (!getUsers().containsKey(id)) {
            throw new NotFoundException("Нет пользователя с таким ID.");
        }
    }

    private void existsUser(User user) {
        if (!getUsers().containsKey(user.getId())) {
            throw new NotFoundException("Нет пользователя с таким ID.");
        }
    }

    private void existsUser(Integer id, Integer otherId) {
        if (!getUsers().containsKey(id) || !getUsers().containsKey(otherId)) {
            throw new NotFoundException("Нет пользователя с таким ID.");
        }
    }
}
