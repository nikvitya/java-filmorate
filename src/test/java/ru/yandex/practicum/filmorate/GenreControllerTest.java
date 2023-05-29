package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.GenreController;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreControllerTest {
    private final GenreController genreController;

    @Test
    void testShowAll() {
        List<Genre> genreList= genreController.showAll();
        assertEquals(genreList.size(),6);
    }

    @Test
    void testGetGenreById() {
        Genre genre1 = genreController.getGenreById(1);
        assertEquals(new Genre(1, "Комедия"),genre1);

    }
}
