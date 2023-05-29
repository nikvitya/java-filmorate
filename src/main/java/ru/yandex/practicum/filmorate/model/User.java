package ru.yandex.practicum.filmorate.model;

import lombok.*;

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
    private String email;

    @NonNull
    private String login;

    private LocalDate birthday;

    private final Set<Integer> friendsId = new HashSet<>();


}
