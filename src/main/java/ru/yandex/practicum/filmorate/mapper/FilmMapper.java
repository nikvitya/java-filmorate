package ru.yandex.practicum.filmorate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.storage.film.dal.GenreStorage;
import ru.yandex.practicum.filmorate.storage.film.dal.RatingMpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class FilmMapper implements RowMapper<Film> {
    private final RatingMpaStorage ratingMpaDbStorage;
    private final GenreStorage genreDbStorage;


    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        Set<Genre> genres = genreDbStorage.getFilmGenresByFilmId(id);
        RatingMpa mpa = ratingMpaDbStorage.getRatingMpaById(rs.getInt("rating_id"));

        return new Film(id,name,description,releaseDate,duration,mpa,genres);

//        private final Set<Integer> userIdsWhoPutLike = new LinkedHashSet<>();
    }
}
