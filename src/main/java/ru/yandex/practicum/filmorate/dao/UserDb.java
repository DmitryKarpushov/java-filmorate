package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Component
public interface UserDb {
    Integer add(User user);

    void update(User user);

    User findById(Integer id);

    List<User> findAll();

    boolean addRequestsFriendship(Integer idUser, Integer idFriend);

    boolean deleteFriends(Integer idUser, Integer idFriend);

    List<Integer> findAllFriends(Integer idUser);
}
