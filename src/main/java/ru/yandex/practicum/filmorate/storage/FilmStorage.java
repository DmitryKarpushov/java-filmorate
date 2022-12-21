package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Component
public interface FilmStorage {

    List<Film> getAll();

    Film getById(Integer id);

    Film add(Film film);

    void delete(Integer id);

    Film update(Film film);
}
