package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;


import java.util.List;

public interface UserStorage {
    public List<User> showAll();
    public User create(User user);
    public User update(User user) ;
    public void deleteAll();
    public User deleteUserById(Integer id);
    public User getUserById(Integer id);

}
