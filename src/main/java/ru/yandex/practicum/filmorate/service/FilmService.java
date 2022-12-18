package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Service
public class FilmService {
    InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Map<Integer, Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.addFilm(film);
    }

    public void deleteFilm(Integer id) {
        inMemoryFilmStorage.deleteFilm(id);
    }

    public void updateFilm(Film film) {
        inMemoryFilmStorage.updateFilm(film);
    }

    public List<Film> getFilmsList() {
        return new ArrayList<>(inMemoryFilmStorage.getFilms().values());
    }

    /**
     * Добавление лайка на фильм
     */
    public void addLikeFilm(Integer filmId, Integer userId) {
        if (inMemoryFilmStorage.getFilms().get(filmId) == null) {
            throw new FilmNotFoundException("Id фильма должно быть больше 1.");
        }
        if (userId < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        if (!inMemoryFilmStorage.getFilms().get(filmId).getUsersLikedFilm().contains(userId)) {
            inMemoryFilmStorage.getFilms().get(filmId).addLike(userId);
        }
    }

    /**
     * Удаление лайка на фильм
     */
    public void deleteLikeFilm(Integer filmId, Integer userId) {
        if (inMemoryFilmStorage.getFilms().get(filmId) == null) {
            throw new FilmNotFoundException("Id фильма должно быть больше 1.");
        }
        if (userId < 1) {
            throw new UserNotFoundException("Id пользователя должно быть больше 1.");
        }
        inMemoryFilmStorage.getFilms().get(filmId).deleteLike(userId);
    }

    /**
     * Вывод популярных фильмов
     */
    public List<Film> getMostPopularMoviesOfLikes(Integer count) {
        if (count < 1) {
            throw new IncorrectParameterException("Значение count должно быть больше 1.");
        }
        Comparator<Film> filmComparator = (film1, film2) -> {
            if (film2.getRating().compareTo(film1.getRating()) == 0) {
                return film1.getName().compareTo(film2.getName());
            }
            return film2.getRating().compareTo(film1.getRating());
        };
        return getFilmsList().stream()
                .sorted(filmComparator)
                .limit(count)
                .collect(Collectors.toList());
    }
}
