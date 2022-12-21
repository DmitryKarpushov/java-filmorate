package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    Set<Integer> usersLikedFilm = new HashSet<>();//Храним список пользователей,лайкнувших фильм

    public Film(String name, String description, LocalDate releaseDate, Long duration, Integer rating) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (rating == null || rating < 0) {
            this.rating = 0;
        } else {
            this.rating = rating;
        }
    }

    public void addLike(Integer idUser) {
        usersLikedFilm.add(idUser);
        rating = rating + usersLikedFilm.size();
    }

    public void deleteLike(Integer idUser) {
        rating = rating - usersLikedFilm.size();
        usersLikedFilm.remove(idUser);
    }
}
