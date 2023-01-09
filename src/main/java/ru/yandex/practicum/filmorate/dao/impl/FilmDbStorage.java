package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDb;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Slf4j
@Component
public class FilmDbStorage implements FilmDb {

    private final JdbcTemplate jdbcTemplate;
    private final MpaService mpaService;
    private final GenreService genreService;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaService mpaService, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    @Override
    public Integer add(Film film) {
        log.info("FilmDbStorage. add.");
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("FILM_ID");
        return simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
    }

    @Override
    public void update(Film film) {
        log.info("FilmDbStorage. update.");
        String sqlQuery = "UPDATE FILMS SET " +
                "FILM_NAME = ?, MPA_ID = ?, FILM_DESCRIPTION = ? , FILM_RELEASE_DATE = ?, FILM_DURATION = ?, FILM_RATE = ?" +
                "WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getMpa().getId()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getRating()
                , film.getId());
    }

    @Override
    public Optional<Film> findById(Integer id) {
        log.info("FilmDbStorage. findById.");
        String sqlQuery = "SELECT FILM_ID, FILM_NAME, MPA_ID, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION, " +
                "FILM_RATE, FILM_RATE_AND_LIKES " +
                "FROM FILMS WHERE FILM_ID = ?";
        return Optional.of(jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id));
    }

    @Override
    public List<Film> findAll() {
        log.info("FilmDbStorage. findAll.");
        String sqlQuery = "SELECT FILM_ID, FILM_NAME, MPA_ID, FILM_DESCRIPTION, FILM_RELEASE_DATE, FILM_DURATION, " +
                "FILM_RATE, FILM_RATE_AND_LIKES " +
                "FROM FILMS";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public boolean setGenre(Integer idFilm, Integer idGenre) {
        log.info("FilmDbStorage. setGenre.");
        if (!findGenreToFilm(idFilm, idGenre)) {
            String sqlQuery = String.format("INSERT INTO FILM_TO_GENRE VALUES (%d, %d)", idFilm, idGenre);
            return jdbcTemplate.update(sqlQuery) == 1;
        }
        return true;
    }

    @Override
    public boolean deleteGenre(Integer idFilm, Integer idGenre) {
        log.info("FilmDbStorage. deleteGenre.");
        if (findGenreToFilm(idFilm, idGenre)) {
            String sqlQuery = "DELETE FROM FILM_TO_GENRE WHERE FILM_ID = ? AND GENRE_ID = ?";
            return jdbcTemplate.update(sqlQuery, idFilm, idGenre) > 0;
        }
        return false;
    }

    @Override
    public boolean addLike(Integer idFilm, Integer idUser) {
        log.info("FilmDbStorage. addLike.");
        if (!findLikeUserToFilm(idFilm, idUser)) {
            String sqlQuery = String.format("INSERT INTO USER_LIKE_FILM VALUES (%d, %d)", idFilm, idUser);
            return jdbcTemplate.update(sqlQuery) == 1;
        }
        return false;
    }

    @Override
    public List<Film> mostPopulars(Integer limit) {
        log.info("FilmDbStorage. mostPopulars.");
        List<Film> allFilms = findAll();
        for (Film film : allFilms) {
            String sqlQueryFindLike = String.format("" +
                    "SELECT COUNT(*)\n" +
                    "FROM USER_LIKE_FILM\n" +
                    "WHERE FILM_ID = %d", film.getId());
            List<Integer> countLikeToFilm = jdbcTemplate.queryForList(sqlQueryFindLike, Integer.class);
            film.setRateAndLikes(film.getRating() + countLikeToFilm.get(0));
            String sqlQueryUpdateFilm = "update FILMS set " +
                    "FILM_RATE_AND_LIKES = ? " +
                    "where FILM_ID = ?";
            jdbcTemplate.update(sqlQueryUpdateFilm
                    , film.getRateAndLikes()
                    , film.getId());
        }

        List<Film> mostPopularFilms = new ArrayList<>();
        String sqlQuery = String.format("SELECT FILM_ID\n" +
                "    FROM FILMS ORDER BY FILM_RATE_AND_LIKES DESC LIMIT %d", limit);
        List<Integer> IdFilms = jdbcTemplate.queryForList(sqlQuery, Integer.class);
        if (IdFilms.isEmpty()) {
            throw new NotFoundException("Список популярных фильмов пуст");
        }
        for(Integer id : IdFilms){
            mostPopularFilms.add(findById(id)
                    .orElseThrow(() ->new NotFoundException("Фильм не найден.")));
        }
        return mostPopularFilms;
    }

    @Override
    public boolean deleteLike(Integer idFilm, Integer idUser) {
        if (findLikeUserToFilm(idFilm, idUser)) {
            String sqlQuery = "DELETE FROM USER_LIKE_FILM WHERE FILM_ID = ? AND USER_ID = ?";
            return jdbcTemplate.update(sqlQuery, idFilm, idUser) > 0;
        }
        return false;
    }

    @Override
    public List<Genre> getGenres(Integer idFilm) {
        String sqlQuery = String.format("SELECT GENRE_ID\n" +
                "FROM FILM_TO_GENRE\n" +
                "WHERE FILM_ID = %d", idFilm);
        List<Integer> idGenres = jdbcTemplate.queryForList(sqlQuery, Integer.class);
        List<Genre> genres = new ArrayList<>();
        for (Integer id : idGenres) {
            genres.add(genreService.getById(id));
        }
        return genres;
    }

    private boolean findGenreToFilm(Integer idFilm, Integer idGenre) {
        String sqlQuery = String.format("SELECT COUNT(*)\n" +
                "FROM FILM_TO_GENRE\n" +
                "WHERE FILM_ID = %d AND GENRE_ID = %d", idFilm, idGenre);
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class) == 1;
    }

    private boolean findLikeUserToFilm(Integer idFilm, Integer idUser) {
        String sqlQuery = String.format("SELECT COUNT(*)\n" +
                "FROM USER_LIKE_FILM\n" +
                "WHERE FILM_ID = %d AND USER_ID = %d", idFilm, idUser);
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class) == 1;
    }

    private Integer getRateAndLikeFilm(Integer idFilm) {
        String sqlQuery = String.format("SELECT FILM_RATE_AND_LIKES\n" +
                "FROM FILMS\n" +
                "WHERE FILM_ID = %d", idFilm);
        List<Integer> countRateAndLike = jdbcTemplate.queryForList(sqlQuery, Integer.class);
        if (countRateAndLike.size() > 0) {
            return countRateAndLike.get(0);
        } else {
            return 0;
        }
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(resultSet.getString("FILM_NAME")
                , resultSet.getString("FILM_DESCRIPTION")
                , resultSet.getDate("FILM_RELEASE_DATE").toLocalDate()
                , resultSet.getLong("FILM_DURATION")
                , resultSet.getInt("FILM_RATE")
                , mpaService.getById(resultSet.getInt("MPA_ID"))
                , new ArrayList<>());
        film.setId(resultSet.getInt("FILM_ID"));
        film.setMpa(mpaService.getById(film.getMpa().getId()));
        film.setGenres(getGenres(film.getId()));
        film.setRateAndLikes(getRateAndLikeFilm(film.getId()));
        return film;
    }
}
