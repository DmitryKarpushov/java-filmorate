package ru.yandex.practicum.filmorate.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

/**
 * @author Дмитрий Карпушов 05.12.2022
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("ErrorHandler. handleNotFoundException. Нет данных.");
        return new ErrorResponse(
                e.getMessage(), "Нет данных."
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final Throwable e) {
        log.info("ErrorHandler. handleServerError. Произошла непредвиденная ошибка.");
        return new ErrorResponse(e.getMessage(),
                "Произошла непредвиденная ошибка."
        );
    }
}
