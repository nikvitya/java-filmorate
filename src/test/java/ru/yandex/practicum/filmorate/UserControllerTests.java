package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;


@SpringBootTest
class UserControllerTests {
    @Autowired
    UserController userController;
    User user;


    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .name("nisi eiusmod")
                .email("n@yandex.ru")
                .login("nikvitya")
                .birthday(LocalDate.of(1967, 03, 25))

                .build();

    }


    @Test
    void shouldThrowExceptionIfEmailIsEmpty() {
        user.setEmail("");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(user);
        });

        assertEquals("Проверьте поля пользователя!",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfEmailDoesNotContainEMailSymbol() {
        user.setEmail("nik yandex.ru");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(user);
        });

        assertEquals("Проверьте поля пользователя!",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfLoginIsBlank() {
        user.setLogin("     ");

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(user);
        });

        assertEquals("Проверьте поля пользователя!",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldThrowExceptionIfBirathDayInFuture() {
        user.setBirthday(LocalDate.of(2100,1,1));

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.create(user);
        });

        assertEquals("Проверьте поля пользователя!",thrown.getMessage());
        assertEquals(400, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldPutLoginIfNameIsEmpty() throws ValidationException {
        user.setName("");

        User newUser = userController.create(user);

        assertEquals(user.getLogin(),newUser.getName());
        assertEquals(202, HttpStatus.ACCEPTED.value());
    }

}
