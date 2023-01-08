package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@Data
public class Genre {

    private Integer id;
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
