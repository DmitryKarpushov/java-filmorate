package ru.yandex.practicum.filmorate.datavalidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class ValidationFieldsFilm {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    public static void noFoundFilm(Film film, Map<Integer, Film> films) {
        if (!films.containsKey(film.getId())) {
            logger.info("ValidationFieldsFilm.isValidDate: Проверяем на существование фильма");
            throw new NotFoundException("Такого фильма не существует");
        }
    }
}
