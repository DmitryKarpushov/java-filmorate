package ru.yandex.practicum.filmorate.validator.impl;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * @author Дмитрий Карпушов 17.11.2022
 */
public class FilmValidator implements ConstraintValidator<FilmValid, Film> {

    @Override
    public boolean isValid(Film film, ConstraintValidatorContext constraintValidatorContext) {
        return !film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
