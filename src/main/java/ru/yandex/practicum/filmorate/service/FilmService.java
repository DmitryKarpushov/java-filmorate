package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

    public List<Film> getFilms() {
        return inMemoryFilmStorage.getAll();
    }

    public Film getFilmById(Integer id) {
        return inMemoryFilmStorage.getById(id);
    }

    public Film addFilm(Film film) {
        return inMemoryFilmStorage.add(film);
    }

    public void deleteFilm(Integer id) {
        inMemoryFilmStorage.delete(id);
    }

    public void updateFilm(Film film) {
        inMemoryFilmStorage.update(film);
    }

    /**
     * Добавление лайка на фильм
     */
    public void addLikeFilm(Integer filmId, Integer userId) {
        if (!inMemoryFilmStorage.getById(filmId).getUsersLikedFilm().contains(userId)) {
            inMemoryFilmStorage.getById(filmId).addLike(userId);
        }
    }

    /**
     * Удаление лайка на фильм
     */
    public void deleteLikeFilm(Integer filmId, Integer userId) {
        existsUser(userId);
        inMemoryFilmStorage.getById(filmId).deleteLike(userId);
    }

    /**
     * Вывод популярных фильмов
     */
    public List<Film> getMostPopularMoviesOfLikes(Integer count) {
        Comparator<Film> filmComparator = (film1, film2) -> {
            if (film2.getRating().compareTo(film1.getRating()) == 0) {
                return film1.getName().compareTo(film2.getName());
            }
            return film2.getRating().compareTo(film1.getRating());
        };
        return inMemoryFilmStorage.getAll().stream()
                .sorted(filmComparator)
                .limit(count)
                .collect(Collectors.toList());
    }

    private void existsUser(Integer userId) {
        if (userId < 1) {
            throw new NotFoundException("Id пользователя должно быть больше 1.");
        }
    }
}
