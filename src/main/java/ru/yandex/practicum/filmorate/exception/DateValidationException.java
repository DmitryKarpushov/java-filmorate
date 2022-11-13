package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class DateValidationException extends RuntimeException{
    public DateValidationException(String message) {
        super(message);
    }
}
