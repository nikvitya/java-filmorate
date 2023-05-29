package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.*;


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
    private RatingMpa mpa;

    private Set<Genre> genres;
    @JsonIgnore
    private final Set<Integer> userIdsWhoPutLike = new LinkedHashSet<>();

}
