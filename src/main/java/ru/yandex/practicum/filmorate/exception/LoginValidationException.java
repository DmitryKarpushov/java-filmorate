package ru.yandex.practicum.filmorate.exception;

/**
 * @author Дмитрий Карпушов 12.11.2022
 */
public class LoginValidationException extends RuntimeException{
    public LoginValidationException(String message) {
        super(message);
    }
}
