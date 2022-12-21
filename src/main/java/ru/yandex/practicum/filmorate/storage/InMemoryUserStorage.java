package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(Integer id) {
        isExist(id);
        return users.get(id);
    }

    @Override
    public User add(User user) {
        int idUser = generateId();
        user.setId(idUser);
        users.put(idUser, user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        users.remove(id);
    }

    @Override
    public User update(User user) {
        isExist(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    private Boolean isExist(Integer id){
        if (!users.containsKey(id)){
            throw new NotFoundException("Данных нет");
        }else {
            return true;
        }
    }
}
