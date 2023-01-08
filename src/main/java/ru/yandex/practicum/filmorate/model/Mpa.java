package ru.yandex.practicum.filmorate.model;

import lombok.Data;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@Data
public class Mpa {

    private Integer id;
    private String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}