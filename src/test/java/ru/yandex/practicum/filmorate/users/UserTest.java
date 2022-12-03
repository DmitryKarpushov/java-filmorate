package ru.yandex.practicum.filmorate.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;

import javax.validation.Validation;

import javax.validation.ValidatorFactory;


import java.time.LocalDate;
import java.util.Set;

/**
 * @author ������� �������� 12.11.2022
 */
public class UserTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    @Test
    @DisplayName("1) �������� ���������. �������� �����-����������� ������")
     void correctlyFilledUserTest() {
        final User user = new User(1, "qw@mail.ru", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty(),"��������� ��� �����");
    }
    @Test
    @DisplayName("2) �������� ���������. �������� �������-����������� Email")
     void correctlyEmailUserTestFirst() {
        final User user = new User(1, "badEmail", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"������� �������� email");
    }
    @Test
    @DisplayName("3) �������� ���������. �������� ������ Email")
    void correctlyEmailUserTestSecond() {
        final User user = new User(1, "", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"������ email");
    }

    @Test
    @DisplayName("4) �������� ���������. �������� ������� ����")
    void correctlyBirthdayUserTest() {
        final User user = new User(1, "qw@mail.ru", "test", "name", LocalDate.of(2025, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"������� ����");
    }
    @Test
    @DisplayName("5) �������� ���������. �������� ������ �����")
    void correctlyLoginUserTest() {
        final User user = new User(1, "qw@mail.ru", "", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"������ �����");
    }

    @Test
    @DisplayName("6) �������� ���������. �������� ����� � �������� � ����")
    void correctlyLoginUserTestSecond() {
        final User user = new User(1, "qw@mail.ru", "da da", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"������ �����");
    }

    @Test
    @DisplayName("7) �������� ���������. �������� ������ �����")
    void correctlyLoginUserTestThird() {
        final User user = new User(1, "qw@mail.ru", "dada", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty(),"������ �����");
    }

}
