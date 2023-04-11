package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;



@Data
@AllArgsConstructor
public class Film {

    private Integer id;
    @NonNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;


}
