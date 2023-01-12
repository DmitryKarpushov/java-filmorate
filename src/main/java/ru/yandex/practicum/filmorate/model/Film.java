package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 * FilmValid - кастомный validator
 */
@Data
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
    Long duration; //продолжительность фильма

    Integer rating;//рейтинг(количество лайков)

    Mpa mpa;
    List<Genre> genres;

    Integer rateAndLikes;

    public Film(String name, String description, LocalDate releaseDate, Long duration, Integer rate,
                Mpa mpa, List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (rate != null) {
            this.rating = rate;
        } else {
            this.rating = 0;
        }
        this.mpa = mpa;
        if (genres == null) {
            this.genres = new ArrayList<>();
        } else {
            this.genres = genres;
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("FILM_NAME", name);
        values.put("FILM_DESCRIPTION", description);
        values.put("FILM_RELEASE_DATE", releaseDate);
        values.put("FILM_DURATION", duration);
        values.put("FILM_RATE", rating);
        values.put("MPA_ID", mpa.getId());
        values.put("FILM_RATE_AND_LIKES", rateAndLikes);
        return values;
    }
}
