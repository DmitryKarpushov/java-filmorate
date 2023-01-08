package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDb;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Slf4j
@Component
public class GenreDbStorage implements GenreDb {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findById(Integer id) {
        log.info("GenreDbStorage. findById.");
        String sqlQuery = String.format("SELECT GENRE_NAME " +
                "FROM GENRE WHERE GENRE_ID = %d", id);
        List<String> names = jdbcTemplate.queryForList(sqlQuery, String.class);
        if (names.size() != 1) {
            throw new NotFoundException("Не корректный ID GENRE");
        }
        return names.get(0);
    }

    @Override
    public List<Genre> findAll() {
        log.info("GenreDbStorage. findAll.");
        String sqlQuery = "SELECT GENRE_ID, GENRE_NAME FROM GENRE";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        log.info("GenreDbStorage. mapRowToGenre.");
        return new Genre(resultSet.getInt("GENRE_ID")
                , resultSet.getString("GENRE_NAME"));
    }
}
