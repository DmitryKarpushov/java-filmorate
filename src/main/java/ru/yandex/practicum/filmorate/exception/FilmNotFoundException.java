package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 18.12.2022
 */
public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String message) {
        super(message);
    }
}


