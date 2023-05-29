package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dal.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int id = 1;
    private final LocalDate localDateNow = LocalDate.now();
    private final Map<Integer, User> users = new HashMap<>();

    private int idGenerator() {
        return id++;
    }


    @Override
    public List<User> showAll() {
        log.info("Получен запрос на показ всех пользователей.В базе {} пользователей.", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
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
                log.warn("Пользователь с id= {} уже добавлен в базу.", user.getId());
                throw new ValidationException("Пользователь с id=" + user.getId() + " уже добавлен в базу");
            }
        }

        user.setId(idGenerator());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@") || user.getLogin().isBlank() || user.getBirthday().isAfter(localDateNow)) {
            log.warn("Поля пользователя введены неверно");
            throw new ValidationException("Проверьте поля пользователя!");
        }

        if (!users.containsKey(user.getId())) {
            log.warn("Пользователя невозможно обновить , id {} нет в базе", user.getId());
            throw new NotFoundException("Пользователя с id= " + user.getId() + " нет в базе");
        } else {
            users.put(user.getId(),user);
        }

        return user;
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public void deleteUserById(Integer id) {

        if (!users.containsKey(id)) {
            log.warn("Пользователя невозможно удалить , id= {} нет в базе", id);
            throw new NotFoundException("Пользователя с id=" + id + " нет в базе");
        }

       // return users.remove(id);

    }

    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            log.warn("Пользователя невозможно найти , id= {} нет в базе", id);
            throw new NotFoundException("Пользователя с id=" + id + " нет в базе");
        }

        return users.get(id);
    }
}
