package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;


import java.util.List;

public interface UserStorage {

    List<User> showAll();

    User create(User user);

    User update(User user);

    void deleteAll();

    User deleteUserById(Integer id);

    User getUserById(Integer id);

}
