package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class FilmService {
    private final FilmStorage inMemoryFilmStorage;
    private final UserStorage inMemoryUserStorage;

    public FilmService(FilmStorage inMemoryFilmStorage, UserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<Film> showAll() {
       return inMemoryFilmStorage.showAll();
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }

    public Film update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public Film deleteFilmById(Integer id) {
        return inMemoryFilmStorage.deleteFilmById(id);
    }

    public void deleteAll() {
        inMemoryFilmStorage.deleteAll();
    }

    public Film getFilmById(Integer id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public User getUserById(Integer userId) {
        return inMemoryUserStorage.getUserById(userId);
    }

    public void addLike(Integer id, Integer userId) {
        Film film = getFilmById(id);
        User user = getUserById(userId);

        if (film == null) {
            log.warn("Фильму невозможно добавить like , фильма с id={} нет в базе", id);
            throw new NotFoundException("Фильм с id=" + id + " нет в базе");
        }

        if (user == null) {
            log.warn("Фильму невозможно добавить like , пользователя с userId={} нет в базе", userId);
            throw new NotFoundException("Пользователя с userId=" + id + " нет в базе");

        }

        film.getUserIdsWhoPutLike().add(userId);

    }

    public void deleteLike(Integer id, Integer userId) {
        Film film = getFilmById(id);
        User user = getUserById(userId);

        if (film == null) {
            log.warn("Фильму невозможно удалить like , фильма с id={} нет в базе", id);
            throw new NotFoundException("Фильм с id=" + id + " нет в базе");
        }

        if (user == null) {
            log.warn("Фильму невозможно удалить like , пользователя с userId={} нет в базе", userId);
            throw new NotFoundException("Пользователя с userId=" + id + " нет в базе");

        }

        film.getUserIdsWhoPutLike().remove(userId);

    }

    public List<Film> getTop10PopularFilms(Integer count) {

       return inMemoryFilmStorage.showAll().stream()
               .sorted(Comparator.comparing((Film film) -> film.getUserIdsWhoPutLike().size()).reversed())
               .limit(count)
               .collect(Collectors.toList());
    }
}
