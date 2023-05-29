package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.user.dao.UserDbStorage;

import java.util.List;


@Service
@Slf4j
public class UserService {
    private final UserDbStorage userDbStorage;

    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }


    public User create(User user) {
        return userDbStorage.create(user);
    }

    public List<User> showAll() {
        return userDbStorage.showAll();
    }

    public User getUserById(Integer id) {
        return userDbStorage.getUserById(id);
    }

    public User update(User user) {
       return userDbStorage.update(user);
    }

    public void deleteUserById(Integer id) {
        userDbStorage.deleteUserById(id);
    }

    public void deleteAll() {
        userDbStorage.deleteAll();
    }



    public void addFriends(Integer id, Integer friendId) {
         userDbStorage.addFriends(id,friendId);

    }

    public List<User> getFriends(Integer id) {
        return userDbStorage.getFriends(id);

    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        return userDbStorage.getCommonFriends(id,otherId);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        userDbStorage.deleteFriend(id,friendId);
    }

}
