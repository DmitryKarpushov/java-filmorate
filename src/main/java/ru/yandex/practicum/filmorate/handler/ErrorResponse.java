package ru.yandex.practicum.filmorate.handler;

/**
 * @author Дмитрий Карпушов 05.12.2022
 */
public class ErrorResponse {
    String error;
    String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}