package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;


public interface FilmStorage {

    public List<Film> showAll();

    public Film create(Film film) throws ValidationException;

    public Film update(Film film) throws ValidationException;

    public void deleteAll();

    public Film deleteFilmById(Integer id);

    public Film getFilmById(Integer id);

}
