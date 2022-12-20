package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
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
        isExist(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(Integer id) {
        isExist(id);
        return films.get(id);
    }

    private Boolean isExist(Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Данных нет");
        } else {
            return true;
        }
    }
}
