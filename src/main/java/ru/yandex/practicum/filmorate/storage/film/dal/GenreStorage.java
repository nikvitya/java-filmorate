package ru.yandex.practicum.filmorate.storage.film.dal;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {

    List<Genre> getAllGenres();

    Genre getGenreById(Integer id);

    Set<Genre> getFilmGenresByFilmId(Integer id);
}
