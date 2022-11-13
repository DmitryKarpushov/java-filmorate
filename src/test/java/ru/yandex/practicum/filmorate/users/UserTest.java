package ru.yandex.practicum.filmorate.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.datavalidation.ValidationFieldsUser;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;

import javax.validation.Validation;

import javax.validation.ValidatorFactory;


import java.time.LocalDate;
import java.util.Set;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class UserTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    @Test
    @DisplayName("1) Проверка валидации. Передаем верно-заполненный объект")
     void correctlyFilledUserTest() {
        final User user = new User(1, "qw@mail.ru", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty(),"Заполнено все верно");
    }
    @Test
    @DisplayName("2) Проверка валидации. Передаем неверно-заполненный Email")
     void correctlyEmailUserTestFirst() {
        final User user = new User(1, "badEmail", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Неверно заполнен email");
    }
    @Test
    @DisplayName("3) Проверка валидации. Передаем пустой Email")
    void correctlyEmailUserTestSecond() {
        final User user = new User(1, "", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Пустой email");
    }

    @Test
    @DisplayName("4) Проверка валидации. Передаем будущую дату")
    void correctlyBirthdayUserTest() {
        final User user = new User(1, "qw@mail.ru", "test", "name", LocalDate.of(2025, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Будущая дата");
    }
    @Test
    @DisplayName("5) Проверка валидации. Передаем пустой логин")
    void correctlyLoginUserTest() {
        final User user = new User(1, "qw@mail.ru", "", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Пустой логин");
    }
    @Test
    @DisplayName("6) Проверка валидации. Проверка логина на пробелы")
    void correctlyLoginTest() {
        final User user = new User(1, "qw@mail.ru", "q w", "name", LocalDate.of(2000, 11, 14));
        Assertions.assertTrue(ValidationFieldsUser.isValidLogin(user.getLogin()), "В логине есть пробелы");
    }
}
