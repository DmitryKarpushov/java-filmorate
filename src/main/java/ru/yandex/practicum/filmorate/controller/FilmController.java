package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.DateValidatorUsingDate;
import ru.yandex.practicum.filmorate.exception.DateValidationException;
import ru.yandex.practicum.filmorate.exception.IdValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
   // private final List<Film> films = new ArrayList<>();
    private Map<Integer,Film> films = new HashMap<>();

    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        logger.info("FilmController.createFilm: Начали добавление фильма");
        if (DateValidatorUsingDate.isValidDate(film.getReleaseDate())){
            throw new DateValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        int idFilm = generateId();
        film.setId(idFilm);
        films.put(idFilm,film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        logger.info("FilmController.createFilm: Начали обновление фильма");
        if (!films.containsKey(film.getId())){
            throw new IdValidationException("FilmController.createFilm: Такого фильма не существует");
        }
        if (DateValidatorUsingDate.isValidDate(film.getReleaseDate())){
            throw new DateValidationException("Дата релиза не должна быть раньше 28 декабря 1895 года");
        }
        films.put(film.getId(),film);
        return film;
    }
}
