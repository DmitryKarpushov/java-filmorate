package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    final FilmService filmService;

    private final int countPopularFilms = 10;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> getFilms() {
        log.info("FilmController. getFilms. Получаем все фильмы.");
        return filmService.getFilmsList();
    }

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("FilmController. createFilm. Добавление фильма.");
        return filmService.addFilm(film);
    }


    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody @NotNull Film film) {
        log.info("FilmController. updateFilm. Обновление фильма.");
        if (!filmService.getFilms().containsKey(film.getId())) {
            throw new FilmNotFoundException("Нет фильма с таким ID.");
        }
        filmService.updateFilm(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable int id) {
        log.info("FilmController. getFilm. Получение фильма.");
        if (!filmService.getFilms().containsKey(id)) {
            throw new FilmNotFoundException("Нет фильма с таким ID.");
        }
        return new ResponseEntity<>(filmService.getFilms().get(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.info("FilmController. getPopularFilms. Получение фильма.");
        if (count == null) {
            count = countPopularFilms;
        }
        return filmService.getMostPopularMoviesOfLikes(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. addLikeFilm. Ставим лайк фильму.");
        if (!filmService.getFilms().containsKey(id)) {
            throw new FilmNotFoundException("Нет фильма с таким ID.");
        }
        filmService.addLikeFilm(id, userId);
        return new ResponseEntity<>(filmService.getFilms().get(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. deleteLikeFilm. Удаляем лайк у фильма.");
        if (!filmService.getFilms().containsKey(id)) {
            throw new FilmNotFoundException("Нет фильма с таким ID.");
        }
        filmService.deleteLikeFilm(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
