package ru.yandex.practicum.filmorate.storage.film.dal;

import ru.yandex.practicum.filmorate.model.RatingMpa;

import java.util.List;

public interface RatingMpaStorage {

    List<RatingMpa> getAllRatingMpa();

    RatingMpa getRatingMpaById(Integer id);

}
