package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;


@SpringBootTest
class FilmControllerTests {

	@Autowired
	FilmController filmController;

	Film film;


	@BeforeEach
	void setUp() {
		film = Film.builder()
				.id(1)
				.name("nisi eiusmod")
				.description("adipisicing")
				.releaseDate(LocalDate.of(1967, 03, 25))
				.duration(100)
				.build();

	}

	@Test
	void shouldThrowExceptionIfNameIsBlank() {
		film.setName("");

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(film);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDescriptionLengthMoreThen200() {
		film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(film);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDateOFReleaseTooEarly() {
		film.setReleaseDate(LocalDate.of(1800,1,1));

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(film);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void shouldThrowExceptionIfDurationIsNegative() {
		film.setDuration(-1);

		ValidationException thrown = assertThrows(ValidationException.class, () -> {
			filmController.create(film);
		});

		assertEquals("Проверьте поля фильма",thrown.getMessage());
		assertEquals(400, HttpStatus.BAD_REQUEST.value());
	}

}
