package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.DateValidatorUsingDate;
import ru.yandex.practicum.filmorate.exception.DateValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final List<Film> films = new ArrayList<>();

    @GetMapping
    public List<Film> findAll() {
        return films;
    }

    @PostMapping
    public void createFilm(@RequestBody @Valid Film film) {
        logger.info("FilmController.createFilm: Начали добавление фильма");
        if (DateValidatorUsingDate.isValidDate(film.getReleaseDate())){
            throw new DateValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        films.add(film);
    }

    @PutMapping
    public void updateFilm(@RequestBody @Valid Film film) {
        logger.info("FilmController.createFilm: Начали обновление фильма");
        if (DateValidatorUsingDate.isValidDate(film.getReleaseDate())){
            throw new DateValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        ListIterator<Film> iterator = films.listIterator();
        while (iterator.hasNext()) {
            Film next = iterator.next();
            if ((next.getId()).equals(film.getId())) {
                iterator.set(film);
            }
        }
    }
}
