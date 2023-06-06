package ru.yandex.practicum.filmorate.storage.film.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.storage.film.dal.RatingMpaStorage;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RatingMpaDbStorage implements RatingMpaStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<RatingMpa> getAllRatingMpa() {
        List<RatingMpa> all = new ArrayList<>();
        String sql = "SELECT * FROM ratings";
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sql);
        while (mpaRow.next()) {
            all.add(new RatingMpa(mpaRow.getInt("id"), mpaRow.getString("name")));
        }

        return all;
    }

    @Override
    public RatingMpa getRatingMpaById(Integer id) {
        String sql = "SELECT * FROM ratings WHERE id = ?";
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet(sql, id);
        if (mpaRow.first()) {
            return new RatingMpa(mpaRow.getInt("id"), mpaRow.getString("name"));
        } else {
            throw new NotFoundException("rating_MPA не найден.");
        }
    }
}
