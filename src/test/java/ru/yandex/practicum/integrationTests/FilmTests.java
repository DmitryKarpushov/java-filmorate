package ru.yandex.practicum.integrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.dao.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.impl.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmTests {

    final FilmDbStorage filmDbStorage;
    final GenreDbStorage genreDbStorage;
    final UserDbStorage userStorage;

    @BeforeEach
    void createdFilmForDB() {
        if (filmDbStorage.findAll().size() != 2) {
            List<Genre> genres = new ArrayList<>();
            genres.add(new Genre(2, genreDbStorage.findById(2)));
            Film film = new Film("Достучатся до небес", "Немецкий кинофильм 1997 года режиссёра Томаса Яна", LocalDate.parse("1997-02-20"),
                    87L, 4, new Mpa(1, "G"), genres);

            filmDbStorage.add(film);
            filmDbStorage.setGenre(1, 2);

            Film filmNext = new Film("Тестовая драмма", "Тестовый фильм", LocalDate.parse("2022-01-01"),
                    75L, 0, new Mpa(2, "PG"), genres);

            filmDbStorage.add(filmNext);
            filmDbStorage.setGenre(2, 2);
        }

        if (userStorage.findAll().size() != 2) {
            User firstTestUser = new User("testUserOne@yandex.ru", "UserOne", "Tester", LocalDate.parse("2000-01-01"));
            userStorage.add(firstTestUser);

            User SecondTestUser = new User("testUserTwo@yandex.ru", "UserTwo", "Toster", LocalDate.parse("2000-02-01"));
            userStorage.add(SecondTestUser);
        }
    }

    @Test
    void testAddFilm() {
        checkFindFilmById(1);
        checkFindFilmById(2);
    }

    @Test
    void testUpgradeFilm() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(2, genreDbStorage.findById(2)));
        Film updateFilm = new Film("Достучатся до небес", "updateTest", LocalDate.parse("1997-02-20"), 87L, 4, new Mpa(1, "G"), genres);
        updateFilm.setId(1);

        filmDbStorage.update(updateFilm);

        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(1);

        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description", "updateTest")
                );
    }

    @Test
    void testFindFilm() {
        checkFindFilmById(1);
    }

    @Test
    void testFindAll() {
        List<Film> current = filmDbStorage.findAll();
        Assertions.assertEquals(2, current.size(), "Не корректное количество фильмов");
    }

//    @Test
//    void testSetGenreFilm() {
//        assertTrue(filmDbStorage.setGenre(1, 1), "Жанр фильма не изменился");
//        List<Genre> genres = new ArrayList<>();
//        genres.add(new Genre(2, genreDbStorage.findById(2)));
//        genres.add(new Genre(1, genreDbStorage.findById(1)));
//
//        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(1);
//
////        assertThat(filmDbStorageFilm)
////                .isPresent()
////                .hasValueSatisfying(film ->
////                        assertThat(film).hasFieldOrPropertyWithValue("genres", genres)
////                );
//
//        filmDbStorage.deleteGenre(1, 1);
//    }

    @Test
    void testDeleteGenreFilm() {
        assertTrue(filmDbStorage.deleteGenre(2, 2), "Жанр фильма не изменился");
        List<Genre> genres = new ArrayList<>();
        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(2);

        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("genres", genres)
                );

        filmDbStorage.setGenre(2, 2);
    }

    @Test
    void testAddLikeFilm() {
        assertTrue(filmDbStorage.addLike(1, 1), "пользователь не лайкнул фильм");
        filmDbStorage.deleteLike(1, 1);
    }

    @Test
    void testDeleteLike() {
        filmDbStorage.addLike(1, 1);
        assertTrue(filmDbStorage.deleteLike(1, 1), "Лайк не удален");
    }

    @Test
    void testListMostPopularFilms() {
        filmDbStorage.addLike(1, 1);
        List<Film> films = filmDbStorage.mostPopulars(1);
        Assertions.assertEquals(1, films.size(), "Размер списка фильмов не соответсвует");
        Optional<Film> filmDbStorageFilm = filmDbStorage.findById(1);

        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("rateAndLikes", film.getRating() + 1)
                );

        films = filmDbStorage.mostPopulars(2);
        Assertions.assertEquals(2, films.size(), "Размер списка фильмов не соответсвует");
        filmDbStorageFilm = filmDbStorage.findById(2);

        assertThat(filmDbStorageFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("rateAndLikes", film.getRating())
                );
    }

    void checkFindFilmById(Integer idFilm) {
        Optional<Film> filmOptional = filmDbStorage.findById(idFilm);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", idFilm)
                );

    }
}
