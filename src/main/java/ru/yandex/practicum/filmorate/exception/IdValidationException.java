package ru.yandex.practicum.filmorate.exception;

/**
 * @author ������� �������� 13.11.2022
 */
public class IdValidationException extends RuntimeException{
    public IdValidationException(String message) {
        super(message);
    }
}
