package ru.yandex.practicum.filmorate.storage.film.dao;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.dal.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.dal.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Primary
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmMapper filmMapper;
    private final UserStorage userDbStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmMapper filmMapper, UserStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmMapper = filmMapper;
        this.userDbStorage = userDbStorage;
    }

    @Override
    public Film create(Film film) {
        validateFilm(film);
        log.info("Получен запрос на создание фильма");

        String sql = "INSERT INTO films (name, description, release_date, duration, rating_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(sql, new String[]{"id"});
            prepareStatement.setString(1, film.getName());
            prepareStatement.setString(2, film.getDescription());
            prepareStatement.setDate(3, Date.valueOf(film.getReleaseDate()));
            prepareStatement.setInt(4, film.getDuration());
            prepareStatement.setInt(5, film.getMpa().getId());
            return prepareStatement;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            film.setId(keyHolder.getKey().intValue());
        }

        if (film.getGenres() != null) {
            setGenres(film);
        }

        log.info("Фильм добавлен в базу");

        return film;
    }

    @Override
    public List<Film> showAll() {
        String sql = "select * from films";

        List<Film> allFilms = jdbcTemplate.query(sql,filmMapper);
        log.info("Получен запрос на показ всех фильмов.В базе {} фильмов.", allFilms.size());

        return allFilms;

    }

    @Override
    public Film getFilmById(Integer id) {
        try {
            log.info("Получен запрос на показ фильма с id= {}", id);
            String sql = "select * from films where id = ?";
            return jdbcTemplate.queryForObject(sql, filmMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильм с id= " + id + " не найден");
        }

    }


    @Override
    public Film update(Film film) {
        validateFilm(film);
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? "
                + "where id = ?";

        int count = jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        if (count != 1) {
            throw new NotFoundException("Невозможно обновить фильм с id= " + film.getId());
        }

        if (film.getGenres() != null) {
            setGenres(film);
        }

        return getFilmById(film.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM films WHERE id IN (SELECT id FROM films)";
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteFilmById(Integer id) {
        String sql = "DELETE FROM films WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void addLikeToFilm(Integer filmId, Integer userId) {
        userDbStorage.getUserById(userId);
        getFilmById(filmId);
        String sql = "insert into film_likes (film_id, user_id) " + "values(?, ?)";

        jdbcTemplate.update(sql, filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        userDbStorage.getUserById(userId);
        getFilmById(filmId);
        String sql = "delete from film_likes where film_id = ? and user_id = ?";

        jdbcTemplate.update(sql, filmId, userId);
    }

    public List<Integer> getLikes(Integer filmId) {
        getFilmById(filmId);
        String sql = "select user_id from film_likes where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), filmId);
    }

    public List<Film> sortByLikes(int count) {
        String sql = "SELECT f.* FROM films f " +
                "LEFT JOIN film_likes fl ON f.id = fl.film_id " +
                "GROUP BY f.id " +
                "ORDER BY COUNT(fl.user_id) DESC " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, filmMapper, count);
    }

    private void setGenres(Film film) {
        Set<Genre> genreSet = new HashSet<>(film.getGenres());

        jdbcTemplate.update("delete from film_genre where film_id = ?", film.getId());

        genreSet = genreSet.stream()
                .collect(Collectors.toSet());

        List<Object[]> list = new ArrayList<>();

        for (Genre genre : genreSet) {
            Object[] values = new Object[]{film.getId(), genre.getId().intValue()};
            list.add(values);
        }

        for (Object[] values : list) {
            jdbcTemplate.update("insert into film_genre (film_id, genre_id) values (?, ?)", values);
        }

        film.setGenres(genreSet);
    }

    private void validateFilm(Film film) {

        LocalDate firstFilm = LocalDate.of(1895,12,28);
        int maxDescriptionLength = 200;

        if (film.getName().isBlank() || film.getDescription().length() > maxDescriptionLength
                || film.getReleaseDate().isBefore(firstFilm) || film.getDuration() < 0) {
            log.warn("Поля фильма введены неверно");
            throw new ValidationException("Проверьте поля фильма");
        }
    }

}
