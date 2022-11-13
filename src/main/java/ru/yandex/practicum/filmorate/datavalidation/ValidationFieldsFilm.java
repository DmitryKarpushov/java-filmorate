package ru.yandex.practicum.filmorate.datavalidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.DateValidationException;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class ValidationFieldsFilm {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);
    public static boolean isValidDate(LocalDate dateStr) {
        logger.info("ValidationFieldsFilm.isValidDate: Проверяем на валидность дату");
        return dateStr.isBefore(LocalDate.of(1895, 12, 28));
    }
    public static void noFoundFilm(Film film, Map<Integer,Film> films) {
        if (!films.containsKey(film.getId())) {
            logger.info("ValidationFieldsFilm.isValidDate: Проверяем на существование фильма");
            throw new IdValidationException("Такого фильма не существует");
        }
    }
    public static void validateFields(Film film) {
        logger.info("ValidationFieldsFilm.validateFields: Проверяем на валидность дату");
        if (isValidDate(film.getReleaseDate())){
            throw new DateValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
    }
}
