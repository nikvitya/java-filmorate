package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.RatingMpaController;
import ru.yandex.practicum.filmorate.model.RatingMpa;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RatingMpaControllerTest {
    private final RatingMpaController ratingMpaController;

    @Test
    void testShowAll() {
        List<RatingMpa> ratingMpaList = ratingMpaController.showAll();
        assertEquals(ratingMpaList.size(),5);
    }

    @Test
    void testGetRatingMpaById() {
        RatingMpa ratingMpa1 = ratingMpaController.getRatingMpaById(1);
        assertEquals(new RatingMpa(1, "G"),ratingMpa1);

    }
}
