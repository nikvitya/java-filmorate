package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserService {
//    Создайте UserService, который будет отвечать за такие операции с пользователями, как добавление в друзья,
//    удаление из друзей, вывод списка общих друзей.
//    Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
//    То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
    private final UserStorage inMemoryUserStorage;

    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }


    public User addFriends(Integer id, Integer friendId) {
        User user = inMemoryUserStorage.getUserById(id);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.getFriendsId().add(friendId);
        friend.getFriendsId().add(id);

        return user;
    }


    public User deleteFriend(Integer id, Integer friendId) {
        User user = inMemoryUserStorage.getUserById(id);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.getFriendsId().remove(friendId);
        friend.getFriendsId().remove(id);

        return user;
    }

    public List<User> getFriends(Integer id) {
        User user = inMemoryUserStorage.getUserById(id);

//        List<User> friends = new ArrayList<>();
//
//        Set<Integer> friendId = user.getFriendsId();
//        for (Integer idFriend : friendId) {
//            User friend = inMemoryUserStorage.getUserById(idFriend);
//            if (friend != null) {
//                friends.add(friend);
//            }
//        }
//
//        return friends;

       return user.getFriendsId().stream().map(inMemoryUserStorage::getUserById).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {


        List<User> userFriends = getFriends(id);
        List<User> otherUserFriends = getFriends(otherId);

        List<User> commonFriends = new ArrayList<>();
        for (User userFriend : userFriends) {
            if (otherUserFriends.contains(userFriend)) {
                commonFriends.add(userFriend);
            }
        }

        return commonFriends;

    }


    public User getUserById(Integer id) {
        return inMemoryUserStorage.getUserById(id);
    }

}
