package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    @NonNull
    private String email;

    @NonNull
    private String login;

    private LocalDate birthday;

    private final Set<Integer> friendsId = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && email.equals(user.email) && login.equals(user.login) && Objects.equals(birthday, user.birthday) && Objects.equals(friendsId, user.friendsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, login, birthday, friendsId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", birthday=" + birthday +
                ", friendsId=" + friendsId +
                '}';
    }
}
