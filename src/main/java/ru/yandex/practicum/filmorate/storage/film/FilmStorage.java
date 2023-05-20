package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;


public interface FilmStorage {

    List<Film> showAll();

    Film create(Film film);

    Film update(Film film);

    void deleteAll();

    Film deleteFilmById(Integer id);

    Film getFilmById(Integer id);
}
