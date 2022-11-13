package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@Data
@AllArgsConstructor
public class User {
    Integer id; //целочисленный идентификатор
    @NotEmpty
    @Email(message = "Неправильно введен Email")
    String email; // электронная почта
    @NotEmpty
    String login; // логин пользователя
    String name; // имя для отображения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    LocalDate birthday; // дата рождения

}
