package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
//    название не может быть пустым;
//    максимальная длина описания — 200 символов;
//    дата релиза — не раньше 28 декабря 1895 года;
//    продолжительность фильма должна быть положительной.
    private int id = 1;
    private final LocalDate FIRST_FILM = LocalDate.of(1895,12,28);
    private Map<Integer, Film> films = new HashMap<>();


    @GetMapping()
    public Collection<Film> showAll() {
        log.info("Получен запрос на показ всех фильмов.В базе {} фильмов.", films.size());
        return films.values();
    }

    @PostMapping()
    public Film create(@RequestBody Film film) throws ValidationException {

        if (film.getName().isBlank() || film.getDescription().length()> 200 || film.getReleaseDate().isBefore(FIRST_FILM) || film.getDuration() < 0 ) {
            log.warn("Поля фильма введены неверно");
            throw new ValidationException("Проверьте поля фильма");
        }

        if (film.getId() != null) {
            if (films.containsKey(film.getId())) {
                log.warn("Фильм с id {} уже добавлен в базу.", film.getId());
                throw new ValidationException("Фильм с id " + film.getId() + " уже добавлен в базу");
            }
        }

        film.setId(idGenerator());
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping()
    public Film update(@RequestBody Film film) throws ValidationException {
        if (film.getName().isBlank() || film.getDescription().length()> 200 || film.getReleaseDate().isBefore(FIRST_FILM) || film.getDuration() < 0 ) {
            log.warn("Поля фильма введены неверно");
            throw new ValidationException("Проверьте поля фильма");
        }

        if (!films.containsKey(film.getId())) {
            log.warn("Фильм невозможно обновить , id {} нет в базе", film.getId());
            throw new ValidationException("Фильм с id " + film.getId() + " нет в базе");
        } else {
            films.put(film.getId(),film);
        }

        return film;
    }


    @DeleteMapping()
    public void deleteAll() {
        films.clear();
    }

    public int idGenerator() {
        return id++;
    }

}
