package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDb;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Slf4j
@Component
public class MpaDbStorage implements MpaDb {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findById(Integer id) {
        log.info("MpaDbStorage. findById.");
        String sqlQuery = String.format("SELECT MPA_NAME " +
                "FROM MPA WHERE MPA_ID = %d", id);
        List<String> names = jdbcTemplate.queryForList(sqlQuery, String.class);
        if (names.size() != 1) {
            throw new NotFoundException("Не корректный ID MPA");
        }

        return names.get(0);
    }

    @Override
    public List<Mpa> findAll() {
        log.info("MpaDbStorage. findAll.");
        String sqlQuery = "SELECT MPA_ID, MPA_NAME FROM MPA";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    private Mpa mapRowToMpa(ResultSet resultSet, int rowNum) throws SQLException {
        log.info("MpaDbStorage. mapRowToMpa.");

        return new Mpa(resultSet.getInt("MPA_ID")
                , resultSet.getString("MPA_NAME"));
    }
}
