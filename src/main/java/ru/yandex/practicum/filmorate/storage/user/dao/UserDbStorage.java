package ru.yandex.practicum.filmorate.storage.user.dao;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dal.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;


@Component
@Primary
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserDbStorage(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }



    @Override
    public User create(User user) {
        validateUser(user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users (name, email, login, birthday) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(sql, new String[]{"id"});
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getEmail());
            prepareStatement.setString(3, user.getLogin());
            prepareStatement.setDate(4, Date.valueOf(user.getBirthday()));
            return prepareStatement;
        }, keyHolder);

        if (keyHolder.getKey() instanceof Number) {
            Number generatedId = keyHolder.getKey();
            user.setId(generatedId.intValue());
        } else {
            throw new IllegalStateException("Не удалось получить сгенерированный ключ");
        }

        log.info("Пользователь добавлен в базу");
        return user;
    }

    @Override
    public List<User> showAll() {
        String sql = "select * from users";

        List<User> allUsers = jdbcTemplate.query(sql, userMapper);
        log.info("Получен запрос на показ всех фильмов.В базе {} фильмов.", allUsers.size());

        return allUsers;
    }

    @Override
    public User getUserById(Integer id) {
        try {
            String sql = "select * from users where  id = ?";
            return jdbcTemplate.queryForObject(sql, userMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Пользователь с id= " + id + " не найден");
        }
    }

    @Override
    public User update(User user) {
        getUser(user.getId());
        validateUser(user);
        String sqlQuery = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
        log.info("Пользователь обновлен");
        return user;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM users WHERE id IN (SELECT id FROM users)";
        jdbcTemplate.update(sql);

    }

    @Override
    public void deleteUserById(Integer id) {
        getUser(id);
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
        log.info("Пользователь с id = " + id + " удален");
    }


    public void addFriends(Integer id, Integer friendId) {
        getUser(id);
        getUser(friendId);

        String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, friendId);
        log.info("Друг добавлен");
    }

    public List<User> getFriends(Integer id) {
        getUser(id);
        String sql = "SELECT u.id, u.name, u.email, u.login, u.birthday " +
                "FROM friends AS f " +
                "JOIN users AS u ON f.friend_id = u.id " +
                "WHERE f.user_id = ? " +
                "ORDER BY u.id";

        return jdbcTemplate.query(sql, userMapper, id);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {

        String sql = "SELECT u.id, u.name, u.email, u.login, u.birthday " +
                "FROM friends AS f " +
                "JOIN users AS u ON f.friend_id = u.id " +
                "WHERE f.user_id = ? " +
                "AND f.friend_id IN (SELECT friend_id FROM friends WHERE user_id = ?)";

        return jdbcTemplate.query(sql, userMapper, id, otherId);
    }


    public void deleteFriend(Integer id, Integer friendId) {
        getUser(id);
        getUser(friendId);
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
        log.info("Друг удален");
    }


    public User getUser(Integer id) {
        try {
            String sql = "select * from users where id = ?";
            return jdbcTemplate.queryForObject(sql, userMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Пользователь с id= " + id + " не найден");
        }
    }

    public void validateUser(User user) {

        if (user.getEmail() == null || !user.getEmail().contains("@") || user.getEmail().isBlank()) {
            throw new ValidationException("Email должен быть заполнен и содержать символ @");
        } else if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            throw new ValidationException("Логин не должен быть пустым и содержать пробелы");
        } else if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }


}
