package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

/**
 * @author Дмитрий Карпушов 06.01.2023
 */
@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    final MpaService mpaService;

    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping()
    public List<Mpa> getAll() {
        log.info("MpaController. getMpa. Получение списка категорий");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mpa> getById(@PathVariable Integer id) {
        log.info("MpaController. getMpaById. Получение категории по id");
        return new ResponseEntity<>(mpaService.getById(id), HttpStatus.OK);
    }
}
