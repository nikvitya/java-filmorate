package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
//   электронная почта не может быть пустой и должна содержать символ @;
//   логин не может быть пустым и содержать пробелы;
//   имя для отображения может быть пустым — в таком случае будет использован логин;
//   дата рождения не может быть в будущем.
    private int id = 1;
    private final LocalDate NOW = LocalDate.now();
    private Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> showAll() {
        log.info("Получен запрос на показ всех пользователей.В базе {} пользователей.", users.size());
        return users.values();
    }

    @PostMapping
    public User create( @RequestBody User user) throws ValidationException {
        if (user.getEmail().isEmpty()|| !user.getEmail().contains("@") || user.getLogin().isBlank() || user.getBirthday().isAfter(NOW)) {
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
    public User update( @RequestBody User user) throws ValidationException {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@") || user.getLogin().isBlank() || user.getBirthday().isAfter(NOW)) {
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
