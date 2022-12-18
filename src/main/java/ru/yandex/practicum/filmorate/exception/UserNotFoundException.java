package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 12.12.2022
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
