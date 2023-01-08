package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Дмитрий Карпушов 10.11.2022
 */
@Data
public class User {

    Integer id;

    @NotEmpty
    @Email(message = "Неправильно введен Email")
    String email;

    @NotEmpty
    @Pattern(regexp = "^\\S*")
    String login;

    String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @PastOrPresent
    LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if(name == null || name.isEmpty() || name.isBlank()){
            this.name = login;
        } else{
            this.name = name;
        }
        this.birthday = birthday;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("USER_EMAIL", email);
        values.put("USER_LOGIN", login);
        values.put("USER_NAME", name);
        values.put("USER_BIRTHDAY", birthday);
        return values;
    }


}
