//package ru.yandex.practicum.filmorate;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.yandex.practicum.filmorate.controller.FilmController;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//@SpringBootTest
//class FilmControllerTests {
//
//	FilmController filmController;
//	Film film;
//
//	private Gson gson = new Gson();
//
//
//	@BeforeEach
//	void setUp() {
//		filmController = new FilmController();
//		film = Film.builder()
//				.name("nisi eiusmod")
//				.description("adipisicing")
//				.releaseDate(LocalDate.of(1967, 03, 25))
//				.duration(100)
//				.build();
//
//	}
//
//
//	@Test
//	void filmCreate() throws ValidationException, IOException,InterruptedException {
//		Film newFilm = filmController.create(film);
//
//		HttpClient client = HttpClient.newHttpClient();
//
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create("http://localhost:8080/films"))
//				.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(film)))
//				.build();
//
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		Film filmFromLocalServer = gson.fromJson(Film.class);
//
//		//assertEquals(200, response.statusCode());
//
//
//		assertEquals(newFilm, filmFromLocalServer);
//
//
//	}
//
//}
