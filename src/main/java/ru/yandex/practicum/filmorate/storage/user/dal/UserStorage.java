package ru.yandex.practicum.filmorate.storage.user.dal;

import ru.yandex.practicum.filmorate.model.User;


import java.util.List;

public interface UserStorage {

    User create(User user);

    List<User> showAll();

    User getUserById(Integer id);

    User update(User user);

    void deleteAll();

    void deleteUserById(Integer id);

}
