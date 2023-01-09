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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author ������� �������� 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserTests {

    final UserDbStorage userStorage;

    @BeforeEach
    void createdUserForDB() {
        if (userStorage.findAll().size() != 2) {
            User firstTestUser = new User("testUserOne@yandex.ru", "UserOne", "Tester", LocalDate.parse("2000-01-01"));
            userStorage.add(firstTestUser);
            User SecondTestUser = new User("testUserTwo@yandex.ru", "UserTwo", "Toster", LocalDate.parse("2000-02-01"));
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
        assertEquals(2, currentList.size(), "�� ���������� ���������� �������������");
    }

    @Test
    void testUpgradeUser() {
        User updateUser = new User("updateUser@yandex.ru",
                "updateUser",
                "UpdateName",
                LocalDate.parse("2000-10-10"));
        updateUser.setId(1);
        userStorage.update(updateUser);

        Optional<User> userStorageUser = userStorage.findById(1);

        Map<String, Object> mapForCheck = new HashMap<>();
        mapForCheck.put("id", updateUser.getId());
        mapForCheck.put("email", updateUser.getEmail());
        mapForCheck.put("login", updateUser.getLogin());
        mapForCheck.put("name", updateUser.getName());
        mapForCheck.put("birthday", updateUser.getBirthday());

        for (Map.Entry<String, Object> entry : mapForCheck.entrySet()) {
            assertThat(userStorageUser)
                    .isPresent()
                    .hasValueSatisfying(user ->
                            assertThat(user).hasFieldOrPropertyWithValue(entry.getKey(), entry.getValue())
                    );
        }
    }

    @Test
    void testFindUserById() {
        checkFindUserById(1);
    }

    @Test
    void testAddRequestsFriendship() {
        assertTrue(userStorage.addRequestsFriendship(1, 2), "������ �� ������ �� ���������");
        assertFalse(userStorage.addRequestsFriendship(1, 2), "������ �� ������ �� ������ ���� ���������");
    }

    @Test
    void testDeleteFriends() {
        userStorage.addRequestsFriendship(1, 2);
        assertTrue(userStorage.deleteFriends(1, 2), "������ �� ������ �� ������");
        assertFalse(userStorage.deleteFriends(1, 2), "������ �� ������ �� ������ ���� ������");
    }

    @Test
    void testFindAllFriends() {
        userStorage.addRequestsFriendship(1, 2);
        List<Integer> listFriendIdOne = userStorage.findAllFriends(1);
        assertEquals(1, listFriendIdOne.size(), "� ������ ������ ������ ���� 1 ����");
        assertEquals(2, (int) listFriendIdOne.get(0), "�������� ID ����� ������ �������� 2");

        List<Integer> listFriendIdTwo = userStorage.findAllFriends(2);
        assertEquals(0, listFriendIdTwo.size(), "� ������ ������ �� ������� ���� ������");

    }

    void checkFindUserById(Integer idUser) {
        Optional<User> userStorageById = userStorage.findById(idUser);

        assertThat(userStorageById)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", idUser)
                );
    }
}
