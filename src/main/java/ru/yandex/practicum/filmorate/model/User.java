package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    @NonNull
    @Email
    private String email;

    @NonNull
    private String login;

    private LocalDate birthday;

    private final Set<Integer> friendsId = new HashSet<>();


}
