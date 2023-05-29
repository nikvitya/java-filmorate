package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.film.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.dal.UserStorage;


import java.util.List;

@Service
@Data
@Slf4j
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private final UserStorage userDbStorage;

    public FilmService(FilmDbStorage filmDbStorage, UserStorage userDbStorage) {
        this.filmDbStorage = filmDbStorage;
        this.userDbStorage = userDbStorage;
    }

    public List<Film> showAll() {
       return filmDbStorage.showAll();
    }

    public Film getFilmById(Integer id) {
        return filmDbStorage.getFilmById(id);
    }

    public Film create(Film film) {
        return filmDbStorage.create(film);
    }

    public Film update(Film film) {
        return filmDbStorage.update(film);
    }

    public void deleteFilmById(Integer id) {
        filmDbStorage.deleteFilmById(id);
    }

    public void deleteAll() {
        filmDbStorage.deleteAll();
    }

    public User getUserById(Integer userId) {
        return userDbStorage.getUserById(userId);
    }

    public void addLike(Integer id, Integer userId) {
        filmDbStorage.addLikeToFilm(id,userId);
    }

    public void deleteLike(Integer id, Integer userId) {
        filmDbStorage.deleteLike(id,userId);

    }

    public List<Film> getTop10PopularFilms(Integer count) {
        return filmDbStorage.sortByLikes(count);
    }
}
