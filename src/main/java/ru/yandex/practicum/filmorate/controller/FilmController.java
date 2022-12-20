package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> getFilms() {
        log.info("FilmController. getFilms. Получаем все фильмы.");
        return filmService.getFilms();
    }

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("FilmController. createFilm. Добавление фильма.");
        return filmService.addFilm(film);
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody @NotNull Film film) {
        log.info("FilmController. updateFilm. Обновление фильма.");
        filmService.updateFilm(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable int id) {
        log.info("FilmController. getFilm. Получение фильма.");
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info("FilmController. getPopularFilms. Получение фильма.");
        return filmService.getMostPopularMoviesOfLikes(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> addLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. addLikeFilm. Ставим лайк фильму.");
        filmService.addLikeFilm(id, userId);
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Film> deleteLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. deleteLikeFilm. Удаляем лайк у фильма.");
        filmService.deleteLikeFilm(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
