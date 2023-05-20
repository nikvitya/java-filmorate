package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Film {

    private Integer id;
    @NonNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    private final Set<Integer> userIdsWhoPutLike = new HashSet<>();

}
