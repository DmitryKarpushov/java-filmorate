package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping()
    public List<Genre> getGenres() {
        log.info("GenreController. getGenres. Получение списка жанров");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Integer id) {
        log.info("GenreController. getGenreById. Получение жанра по id");
        return new ResponseEntity<>(genreService.getById(id), HttpStatus.OK);
    }
}