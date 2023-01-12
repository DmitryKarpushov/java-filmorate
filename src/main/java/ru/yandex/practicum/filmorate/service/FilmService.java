package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDb;
import ru.yandex.practicum.filmorate.dao.GenreDb;
import ru.yandex.practicum.filmorate.dao.UserDb;
import ru.yandex.practicum.filmorate.dao.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.*;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Service
public class FilmService {

    final FilmDb filmDbStorage;
    final MpaService mpaService;
    final GenreService genreService;

    @Autowired
    public FilmService(FilmDb filmDbStorage, MpaService mpaService,
                       GenreService genreService) {
        this.filmDbStorage = filmDbStorage;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public Film getById(Integer id) {
        if (id < 9900) {
            var result = filmDbStorage.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
            result.setGenres(genreService.getGenresId(result.getId()));
            return result;
        } else {
            throw new NotFoundException("Пользователь не найден.");
        }
    }

    public Film add(Film film) {
        film.setId(filmDbStorage.add(film));
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        List<Genre> actualGenreFilm = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            actualGenreFilm.add(genreService.getById(genre.getId()));
            if (!filmDbStorage.setGenre(film.getId(), genre.getId())) {
                throw new NotFoundException("Не удалось установить жанр для фильма");
            }
        }
        film.setGenres(actualGenreFilm);
        return film;
    }


    public void update(Film film) {
        getById(film.getId());
        filmDbStorage.update(film);
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        List<Genre> actualGenreFilm = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            if (!actualGenreFilm.contains(genreService.getById(genre.getId()))) {
                actualGenreFilm.add(genreService.getById(genre.getId()));
            }
            if (!filmDbStorage.setGenre(film.getId(), genre.getId())) {
                throw new NotFoundException("Не удалось установить жанр для фильма");
            }
        }

        List<Genre> currentGenreFilm = genreService.getGenresId(film.getId());
        for (Genre current : currentGenreFilm) {
            if (!actualGenreFilm.contains(current)) {
                filmDbStorage.deleteGenre(film.getId(), current.getId());
            }
        }
        film.setGenres(actualGenreFilm);
    }

    /**
     * Запрос на все фильмы
     */
    public List<Film> getAll() {
        var result = filmDbStorage.findAll();
        for (Film film : result) {
            film.setGenres(genreService.getGenresId(film.getId()));
        }

        return result;
    }

    /**
     * запрос в хранилище жанров на все жанры для выбранных фильмов
     */
    public List<Genre> getGenresFilm(Integer filmId) {
        return genreService.getGenresId(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        existsUser(userId);
        if (!filmDbStorage.addLike(filmId, userId)) {
            throw new NotFoundException("Не удалось поставить лайк");
        }
    }

    public void deleteLike(Integer idFilm, Integer userId) {
        existsUser(userId);
        if (!filmDbStorage.deleteLike(idFilm, userId)) {
            throw new NotFoundException("Не корректный запрос на удаление лайка");
        }
    }

    public List<Film> getMostPopularMoviesOfLikes(Integer count) {
        return filmDbStorage.mostPopulars(count);
    }

    private void existsUser(Integer userId) {
        if (userId < 1) {
            throw new NotFoundException("Id пользователя должно быть больше 1.");
        }
    }
}
