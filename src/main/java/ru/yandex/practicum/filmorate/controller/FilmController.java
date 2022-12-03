package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.datavalidation.ValidationFieldsFilm;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

/**
 * @author ������� �������� 10.11.2022
 */
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Logger logger = LoggerFactory.getLogger(FilmController.class);
    private final Map<Integer, Film> films = new HashMap<>();
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
        logger.info("FilmController.createFilm: ������ ���������� ������");
        int idFilm = generateId();
        film.setId(idFilm);
        films.put(idFilm, film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        logger.info("FilmController.createFilm: ������ ���������� ������");
        ValidationFieldsFilm.noFoundFilm(film, films);
        films.put(film.getId(), film);
        return film;
    }
}
