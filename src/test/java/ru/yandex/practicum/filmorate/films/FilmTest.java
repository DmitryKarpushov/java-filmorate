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
 * @author Дмитрий Карпушов 13.11.2022
 */
public class FilmTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    @Test
    @DisplayName("1) Проверка валидации. Передаем верно-заполненный объект. correctlyLoginUserTest")
    void correctlyFieldFilmTest() {
        final Film film = new Film(1, "Титаник", "Корабль женщина смерть", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty(), "Заполнено все верно");
    }

    @Test
    @DisplayName("2) Проверка валидации. Название фильма не может быть пустым")
    void correctlyNameFilmTest() {
        final Film film = new Film(1, "", "Корабль женщина смерть", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Пустое название фильма");
    }

    @Test
    @DisplayName("3) Проверка валидации. Описание max=200, в примере 201")
    void correctlyDescriptionFilmTest() {
        final Film film = new Film(1, "Титаник", "_________________________________________" +
                "________________________________________________________________________________________" +
                "________________________________________________________________________", LocalDate.of(2000, 11, 14), 160);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Описание более 200 символов");
    }

    @Test
    @DisplayName("4) Проверка валидации. Продолжительность фильмов должна быть больше 0")
    void correctlyDurationFilmTest() {
        final Film film = new Film(1, "Титаник", "Корабль женщина смерть", LocalDate.of(2000, 11, 14), -5);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Продолжительность фильмов должна быть больше 0");
    }

    @Test
    @DisplayName("5) Проверка валидации. Проверка даты на 1895-12-28")
    void correctlyDateFilmTestFirst() {
        final Film film = new Film(1, "Титаник", "Корабль женщина смерть", LocalDate.of(1985, 12, 27), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        System.out.println(violations);
        Assertions.assertTrue(violations.isEmpty(), "Фильм не раньше 1895-12-28");
    }

    @Test
    @DisplayName("6) Проверка валидации. Проверка даты на 1895-12-28")
    void correctlyDateFilmTestSecond() {
        final Film film = new Film(1, "Титаник", "Корабль женщина смерть", LocalDate.of(1795, 12, 27), 100);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Фильм раньше 1895-12-28");
    }
}
