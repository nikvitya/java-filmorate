package ru.yandex.practicum.filmorate.storage.film.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.dal.GenreStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();

        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("SELECT * FROM genres ORDER BY id");
        while (mpaRows.next()) {
            genres.add(new Genre(
                    mpaRows.getInt("id"),
                    mpaRows.getString("name")));
        }
        return genres;
    }

    @Override
    public Genre getGenreById(Integer id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet(sql, id);
        if (genreRow.next()) {
            return new Genre(
                    genreRow.getInt("id"),
                    genreRow.getString("name")
            );
        } else {
            throw new NotFoundException("Жанр не найден.");
        }
    }

    @Override
    public Set<Genre> getFilmGenresByFilmId(Integer id) {
        String sql = "SELECT * FROM genres " +
                "INNER JOIN film_genre fg ON genres.id = fg.genre_id " +
                "WHERE fg.film_id = ?";

        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            Integer genreId = rs.getInt("id");
            String genreName = rs.getString("name");
            return new Genre(genreId, genreName);
        }, id));
    }

}
