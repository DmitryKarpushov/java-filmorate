package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 13.11.2022
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
