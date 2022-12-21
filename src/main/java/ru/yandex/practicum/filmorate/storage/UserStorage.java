package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


import java.util.List;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Component
public interface UserStorage {

    List<User> getAll();

    User getById(Integer id);

    User add(User user);

    User update(User user);

    void delete(Integer id);

}
