package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.storage.film.dao.RatingMpaDbStorage;

import java.util.List;

@Service
@Data
public class RatingMpaService {

    private final RatingMpaDbStorage ratingMpaDbStorage;

    public RatingMpaService(RatingMpaDbStorage ratingMpaDbStorage) {
        this.ratingMpaDbStorage = ratingMpaDbStorage;
    }

    public List<RatingMpa> showAll() {
        return ratingMpaDbStorage.getAllRatingMpa();

    }


    public RatingMpa getRatingMpaById(Integer id) {
        return ratingMpaDbStorage.getRatingMpaById(id);


    }
}
