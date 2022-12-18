package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 12.12.2022
 */
public class IncorrectParameterException extends RuntimeException {

    public IncorrectParameterException(String message) {
        super(message);
    }
}
