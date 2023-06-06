package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMpa;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmControllerTests {
	private final FilmController filmController;

	Film testFilm;

	@BeforeEach
	void setUp() {
		testFilm = Film.builder()
				.name("nisi eiusmod")
				.description("adipisicing")
				.releaseDate(LocalDate.of(1967, 03, 25))
				.duration(100)
				.mpa(new RatingMpa(1, "G"))
				.genres(new HashSet<>())
				.build();

	}

	@AfterEach
	void afterEach() {
		filmController.deleteAll();
	}

	@Test
	void shouldThrowExceptionIfNameIsBlank() {
		testFilm.setName("");

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(testFilm);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDescriptionLengthMoreThen200() {
		testFilm.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(testFilm);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDateOFReleaseTooEarly() {
		testFilm.setReleaseDate(LocalDate.of(1800,1,1));

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(testFilm);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDurationIsNegative() {
		testFilm.setDuration(-1);

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(testFilm);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void testCreateFilm() {
		Film newFilm = filmController.create(testFilm);
		assertEquals(testFilm,newFilm);
	}

	@Test
	void testShowAllFilms() {
		filmController.create(testFilm);
		List<Film> films = filmController.showAll();

		assertEquals(List.of(testFilm),films);
	}

	@Test
	void testGetFilmById() {
		Film newFilm = filmController.create(testFilm);
		Film filmId1 = filmController.getFilmById(newFilm.getId());

		assertEquals(newFilm,filmId1);
	}

	@Test
	void testUpdateFilm() {
		Film newFilm = filmController.create(testFilm);
		newFilm.setName("Updated");
		newFilm.setDuration(200);

		Film updatedFilm = filmController.update(newFilm);

		assertEquals(newFilm,updatedFilm);
	}

	@Test
	void testDeleteFilm() {
		Film newFilm = filmController.create(testFilm);
		filmController.deleteFilmById(newFilm.getId());

		List<Film> films = filmController.showAll();

		assertEquals(films.size(),0);
	}


}
