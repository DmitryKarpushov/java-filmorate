package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@Data
@AllArgsConstructor
/**
 * FilmValid - кастомный validator
 * */
@FilmValid
public class Film {
    Integer id; //целочисленный идентификатор
    @NotEmpty(message = "Имя не может быть пустым")
    String name; // название
    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    String description;//описание
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate releaseDate; //дата релиза
    @Positive
    long duration; //продолжительность фильма
}
