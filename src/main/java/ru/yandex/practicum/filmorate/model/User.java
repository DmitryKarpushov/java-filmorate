package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    Set<Integer> friends = new HashSet<>();

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    public void addFriend(Integer idFriend) {
        if (idFriend > 0) {
            friends.add(idFriend);
        } else {
            throw new NotFoundException("Для добавления в друзья,должен быть положительный ID");
        }
    }

    public void deleteFriend(Integer idFriend) {
        if (idFriend > 0) {
            friends.remove(idFriend);
        } else {
            throw new NotFoundException("Для удаления из друзей,должен быть положительный ID");
        }
    }
}
