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

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTests {
    private final UserController userController;
    User testUser;


    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .name("nisi eiusmod")
                .email("n@yandex.ru")
                .login("nikvitya")
                .birthday(LocalDate.of(1967, 03, 25))
                .build();

    }

    @AfterEach
    void afterEach() {
        userController.deleteAll();
    }


    @Test
    void shouldThrowExceptionIfEmailIsEmpty() {
        testUser.setEmail("");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(testUser);
        });

        assertEquals("Email должен быть заполнен и содержать символ @",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfEmailDoesNotContainEMailSymbol() {
        testUser.setEmail("nik yandex.ru");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(testUser);
        });

        assertEquals("Email должен быть заполнен и содержать символ @",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfLoginIsBlank() {
        testUser.setLogin("     ");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(testUser);
        });

        assertEquals("Логин не должен быть пустым и содержать пробелы",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfBirathDayInFuture() {
        testUser.setBirthday(LocalDate.of(2100,1,1));

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(testUser);
        });

        assertEquals("Дата рождения не может быть в будущем",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldPutLoginIfNameIsEmpty() throws ValidationException {
        testUser.setName("");

        User newUser = userController.create(testUser);

        assertEquals(testUser.getLogin(),newUser.getName());
        assertEquals(202, HttpStatus.ACCEPTED.value());
    }


    @Test
    void testCreateUser() {
        User newUser = userController.create(testUser);
        assertEquals(testUser,newUser);
    }

    @Test
    void testShowAllUser() {
        userController.create(testUser);
        List<User> films = userController.showAll();

        assertEquals(List.of(testUser),films);
    }

    @Test
    void testGetFilmById() {
        User newUser = userController.create(testUser);
        User userId1 = userController.getUserById(newUser.getId());

        assertEquals(newUser,userId1);
    }

    @Test
    void testUpdateFilm() {
        User newUser = userController.create(testUser);
        newUser.setName("Updated");
        newUser.setEmail("new@ya.ru");

        User updatedUser = userController.update(newUser);

        assertEquals(newUser,updatedUser);
    }

    @Test
    void testDeleteFilm() {
        User newUser = userController.create(testUser);
        userController.deleteUserById(newUser.getId());

        List<User> users = userController.showAll();

        assertEquals(users.size(),0);
    }


}
