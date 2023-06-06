package ru.yandex.practicum.filmorate.service;


import lombok.Data;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.dal.GenreStorage;

import java.util.List;

@Service
@Data
public class GenreService {
    private final GenreStorage genreDbStorage;

    public GenreService(GenreStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public List<Genre> showAll() {
        return genreDbStorage.getAllGenres();
    }

    public Genre getGenreById(Integer id) {
        return genreDbStorage.getGenreById(id);
    }
}
