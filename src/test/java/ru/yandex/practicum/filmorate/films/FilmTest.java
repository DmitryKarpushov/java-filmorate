package ru.yandex.practicum.filmorate.films;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author ������� �������� 13.11.2022
 */
public class FilmTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    @Test
    @DisplayName("1) �������� ���������. �������� �����-����������� ������. correctlyLoginUserTest")
    void correctlyFieldFilmTest() {
        final Film film = new Film(1, "�������", "������� ������� ������", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty(), "��������� ��� �����");
    }

    @Test
    @DisplayName("2) �������� ���������. �������� ������ �� ����� ���� ������")
    void correctlyNameFilmTest() {
        final Film film = new Film(1, "", "������� ������� ������", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "������ �������� ������");
    }

    @Test
    @DisplayName("3) �������� ���������. �������� max=200, � ������� 201")
    void correctlyDescriptionFilmTest() {
        final Film film = new Film(1, "�������", "_________________________________________" +
                "________________________________________________________________________________________" +
                "________________________________________________________________________", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "�������� ����� 200 ��������");
    }

    @Test
    @DisplayName("4) �������� ���������. ����������������� ������� ������ ���� ������ 0")
    void correctlyDurationFilmTest() {
        final Film film = new Film(1, "�������", "������� ������� ������", LocalDate.of(2000, 11, 14), -5);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "����������������� ������� ������ ���� ������ 0");
    }

    @Test
    @DisplayName("5) �������� ���������. �������� ���� �� 1895-12-28")
    void correctlyDateFilmTestFirst() {
        final Film film = new Film(1, "�������", "������� ������� ������", LocalDate.of(1985, 12, 27), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        System.out.println(violations);
        Assertions.assertTrue(violations.isEmpty(), "����� �� ������ 1895-12-28");
    }

    @Test
    @DisplayName("6) �������� ���������. �������� ���� �� 1895-12-28")
    void correctlyDateFilmTestSecond() {
        final Film film = new Film(1, "�������", "������� ������� ������", LocalDate.of(1795, 12, 27), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "����� ������ 1895-12-28");
    }
}
