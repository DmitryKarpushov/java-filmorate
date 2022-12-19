package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Дмитрий Карпушов 03.12.2022
 */
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    private Integer idTask = 0;

    private Integer generateId() {
        idTask++;
        return idTask;
    }

    @Override
    public Map<Integer, Film> getAll() {
        return films;
    }

    @Override
    public Film add(Film film) {
        int idFilm = generateId();
        film.setId(idFilm);
        films.put(idFilm, film);
        return film;
    }

    @Override
    public void delete(Integer id) {
        films.remove(id);
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(Integer id) {
        return films.get(id);
    }
}
