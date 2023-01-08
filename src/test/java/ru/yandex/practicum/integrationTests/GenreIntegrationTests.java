package ru.yandex.practicum.integrationTests;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.GenreDbStorage;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
class GenreIntegrationTests {
    final GenreDbStorage genreDbStorage;

    @Test
    void testFindNameGenre() {
        LinkedList<String> nameGenre = new LinkedList<>();
        nameGenre.add("Комедия");
        nameGenre.add("Драма");
        nameGenre.add("Мультфильм");
        nameGenre.add("Триллер");
        nameGenre.add("Документальный");
        nameGenre.add("Боевик");
        for (int i = 0; i < nameGenre.size(); i++) {
            assertFalse(genreDbStorage.findById(i + 1).equals(nameGenre.get(i)), "Не корректное название жанра");
        }
    }

    @Test
    void testFindAll() {
        assertTrue(genreDbStorage.findAll().size() == 6, "Размер коллекции жанров не соответсвует");
    }
}
