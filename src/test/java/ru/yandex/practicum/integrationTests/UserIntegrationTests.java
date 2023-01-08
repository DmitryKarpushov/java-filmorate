package ru.yandex.practicum.integrationTests;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserIntegrationTests {

    final UserDbStorage userStorage;

    @BeforeEach
    void createdUserForDB() {
        if (userStorage.findAll().size() != 2) {
            User firstTestUser = new User("testUserOne@yandex.ru",
                    "UserOne",
                    "Tester",
                    LocalDate.parse("2000-01-01"));
            userStorage.add(firstTestUser);
            User SecondTestUser = new User("testUserTwo@yandex.ru",
                    "UserTwo",
                    "Toster",
                    LocalDate.parse("2000-02-01"));
            userStorage.add(SecondTestUser);
        }
        userStorage.deleteFriends(1, 2);
    }

    @Test
    void testCreatedUser() {
        checkFindUserById(1);
        checkFindUserById(2);
    }

    @Test
    void testFindAll() {
        List<User> currentList = userStorage.findAll();
        assertTrue(currentList.size() == 2, "Не корректное количество пользователей");
    }

    @Test
    void testUpgradeUser() {
        User updateUser = new User("updateUser@yandex.ru",
                "updateUser",
                "UpdateName",
                LocalDate.parse("2000-10-10"));
        updateUser.setId(1);
        userStorage.update(updateUser);

        User user = userStorage.findById(1);

        Map<String, Object> mapForCheck = new HashMap<>();
        mapForCheck.put("id", updateUser.getId());
        mapForCheck.put("email", updateUser.getEmail());
        mapForCheck.put("login", updateUser.getLogin());
        mapForCheck.put("name", updateUser.getName());
        mapForCheck.put("birthday", updateUser.getBirthday());
    }

    @Test
    void testFindUserById() {
        checkFindUserById(1);
    }

    @Test
    void testAddRequestsFriendship() {
        assertTrue(userStorage.addRequestsFriendship(1, 2), "Запрос на дружбу не отправлен");
        assertFalse(userStorage.addRequestsFriendship(1, 2), "Запрос на дружбу не должен быть отправлен");
    }

    @Test
    void testDeleteFriends() {
        userStorage.addRequestsFriendship(1, 2);
        assertTrue(userStorage.deleteFriends(1, 2), "Запрос на дружбу не удален");
        assertFalse(userStorage.deleteFriends(1, 2), "Запрос на дружбу не должен быть удален");
    }

    @Test
    void testFindAllFriends() {
        userStorage.addRequestsFriendship(1, 2);
        List<Integer> listFriendIdOne = userStorage.findAllFriends(1);
        assertTrue(listFriendIdOne.size() == 1, "В списке друзей должен быть 1 друг");
        assertTrue(listFriendIdOne.get(0) == 2, "Значение ID друга должно равнятся 2");

        List<Integer> listFriendIdTwo = userStorage.findAllFriends(2);
        assertTrue(listFriendIdTwo.size() == 0, "В списке друзей НЕ должено быть друзей");

    }

    void checkFindUserById(Integer idUser) {
        User user = userStorage.findById(idUser);

    }
}
