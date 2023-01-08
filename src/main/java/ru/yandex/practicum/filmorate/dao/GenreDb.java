package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Component
public interface GenreDb {

    String findById(Integer id);

    List<Genre> findAll();
}
