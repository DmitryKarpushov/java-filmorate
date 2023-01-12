package ru.yandex.practicum.integrationTests;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.MpaDbStorage;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaTests {

    final MpaDbStorage mpaDbStorage;

    @Test
    void testFindNameMpa() {
        LinkedList<String> nameMpa = new LinkedList<>();
        nameMpa.add("G");
        nameMpa.add("PG");
        nameMpa.add("PG-13");
        nameMpa.add("R");
        nameMpa.add("NC-17");
        for (int i = 0; i < nameMpa.size(); i++) {
            assertEquals(mpaDbStorage.findById(i + 1), nameMpa.get(i), "Не корректное название рейтинга");
        }
    }

    @Test
    void testFindAll() {
        assertEquals(5, mpaDbStorage.findAll().size(), "Размер коллекции рейтингов MPA не соответсвует");
    }
}
