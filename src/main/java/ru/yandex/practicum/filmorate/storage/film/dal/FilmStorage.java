package ru.yandex.practicum.filmorate.storage.film.dal;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;


public interface FilmStorage {

    List<Film> showAll();
    Film getFilmById(Integer id);

    Film create(Film film);

    Film update(Film film);

    void deleteAll();

    void deleteFilmById(Integer id);

}
