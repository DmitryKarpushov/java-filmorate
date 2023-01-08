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
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> getFilms() {
        log.info("FilmController. getFilms. Получаем все фильмы.");
        return filmService.getAll();
    }

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("FilmController. createFilm. Добавление фильма.");
        return filmService.add(film);
    }

    @PutMapping()
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody @NotNull Film film) {
        log.info("FilmController. updateFilm. Обновление фильма.");
        filmService.update(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        log.info("FilmController. getFilmById. Получение фильма.");
        return new ResponseEntity<>(filmService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        log.info("FilmController. getPopularFilms. Получение фильма.");
        return new ResponseEntity<>(filmService.getMostPopularMoviesOfLikes(count), HttpStatus.OK);
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity addLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. addLikeFilm. Ставим лайк фильму.");
        filmService.addLike(id, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity deleteLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("FilmController. deleteLikeFilm. Удаляем лайк у фильма.");
        filmService.deleteLike(id, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
