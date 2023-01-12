package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDb;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

/**
 * @author Дмитрий Карпушов 08.01.2023
 */
@Service
public class MpaService {
    final MpaDb mpaDbStorage;

    @Autowired
    public MpaService(MpaDb mpaDbStorage){
        this.mpaDbStorage = mpaDbStorage;
    }

    public List<Mpa> getAll(){
        return mpaDbStorage.findAll();
    }

    public Mpa getById(Integer id){
        return new Mpa(id, mpaDbStorage.findById(id));
    }
}
