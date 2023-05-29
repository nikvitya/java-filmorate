package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dal.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 1;
    private final LocalDate firstFilm = LocalDate.of(1895,12,28);
    private final Map<Integer, Film> films = new HashMap<>();


    private int idGenerator() {
        return id++;
    }

    @Override
    public List<Film> showAll() {
        log.info("Получен запрос на показ всех фильмов.В базе {} фильмов.", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(Integer id) {
        if (!films.containsKey(id)) {
            log.warn("Фильм невозможно получить , id {} нет в базе", id);
            throw new NotFoundException("Фильм с id " + id + " нет в базе");
        }

        return films.get(id);
    }

    @Override
    public Film create(Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getReleaseDate().isBefore(firstFilm) || film.getDuration() < 0) {
            log.warn("Поля фильма введены неверно");
            throw new ValidationException("Проверьте поля фильма");
        }

        if (film.getId() != null) {
            if (films.containsKey(film.getId())) {
                log.warn("Фильм с id {} уже добавлен в базу.", film.getId());
                throw new ValidationException("Фильм с id " + film.getId() + " уже добавлен в базу.");
            }
        }

        film.setId(idGenerator());
        films.put(film.getId(), film);

        return film;

    }

    @Override
    public Film update(Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getReleaseDate().isBefore(firstFilm) || film.getDuration() < 0) {
            log.warn("Поля фильма введены неверно");
            throw new ValidationException("Проверьте поля фильма");
        }

        if (!films.containsKey(film.getId())) {
            log.warn("Фильм невозможно обновить , id {} нет в базе", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " нет в базе");
        } else {
            films.put(film.getId(),film);
        }

        return film;
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    @Override
    public void deleteFilmById(Integer id) {
        if (!films.containsKey(id)) {
            log.warn("Фильм невозможно удалить , id {} нет в базе", id);
            throw new NotFoundException("Фильм с id " + id + " нет в базе");
        }

        //return films.remove(id);
    }




}
