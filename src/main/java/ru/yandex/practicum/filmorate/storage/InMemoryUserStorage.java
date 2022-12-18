package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();
    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public User addUser(User user) {
        int idUser = generateId();
        user.setId(idUser);
        users.put(idUser, user);
        return user;
    }

    @Override
    public void deleteUser(Integer id) {
        if (users.containsKey(id)) {
            users.remove(id);
        }
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
