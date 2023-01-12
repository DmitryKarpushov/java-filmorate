package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Component
public interface FilmDb {

    Integer add(Film film);

    void update(Film film);

    Optional<Film> findById(Integer id);

    List<Film> findAll();

    boolean setGenre(Integer idFilm, Integer idGenre);

    boolean deleteGenre(Integer idFilm, Integer idGenre);

    boolean addLike(Integer idFilm, Integer idUser);

    List<Film> mostPopulars(Integer limit);

    boolean deleteLike(Integer idFilm, Integer idUser);

   // List<Genre> getGenres(Integer filmId);
}