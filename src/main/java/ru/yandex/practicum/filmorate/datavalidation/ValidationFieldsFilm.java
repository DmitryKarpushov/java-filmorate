package ru.yandex.practicum.filmorate.datavalidation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
@Slf4j
public class ValidationFieldsFilm {
    public static void noFoundFilm(Film film, Map<Integer, Film> films) {
        if (!films.containsKey(film.getId())) {
            log.info("ValidationFieldsFilm.isValidDate: Проверяем на существование фильма");
            throw new FilmNotFoundException("Такого фильма не существует");
        }
    }
}
