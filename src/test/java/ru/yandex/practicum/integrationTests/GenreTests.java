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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreTests {

    final GenreDbStorage genreDbStorage;

    @Test
    void testFindAll() {
        assertEquals(6, genreDbStorage.findAll().size(), "Размер коллекции жанров не соответствует");
    }
}
