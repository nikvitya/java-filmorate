package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private int id = 1;
    private final LocalDate localDateNow = LocalDate.now();
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public ArrayList<User> showAll() {
        log.info("Получен запрос на показ всех пользователей.В базе {} пользователей.", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@") || user.getLogin().isBlank() || user.getBirthday().isAfter(localDateNow)) {
            log.warn("Поля пользователя введены неверно");
            throw new ValidationException("Проверьте поля пользователя!");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            log.warn("Поле пользователя \"Имя\" пустое.Переназначено на \"Логин\"");
            user.setName(user.getLogin());
        }

        if (user.getId() != null) {
            if (users.containsKey(user.getId())) {
                log.warn("Пользователь с id {} уже добавлен в базу.", user.getId());
                throw new ValidationException("Пользователь с id " + user.getId() + " уже добавлен в базу");
            }
        }

        user.setId(idGenerator());
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@") || user.getLogin().isBlank() || user.getBirthday().isAfter(localDateNow)) {
            log.warn("Поля пользователя введены неверно");
            throw new ValidationException("Проверьте поля пользователя!");
        }

        if (!users.containsKey(user.getId())) {
            log.warn("Фильм невозможно обновить , id {} нет в базе", user.getId());
            throw new ValidationException("Фильм с id " + user.getId() + " нет в базе");
        } else {
            users.put(user.getId(),user);
        }

        return user;
    }

    @DeleteMapping
    public void delete() {
        users.clear();
    }

    private int idGenerator() {
        return id++;
    }

}
