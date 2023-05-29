package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.*;


@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return duration == film.duration && Objects.equals(id, film.id) && name.equals(film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(mpa, film.mpa) && Objects.equals(genres, film.genres) && Objects.equals(userIdsWhoPutLike, film.userIdsWhoPutLike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration, mpa, genres, userIdsWhoPutLike);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", mpa=" + mpa +
                ", genres=" + genres +
                ", userIdsWhoPutLike=" + userIdsWhoPutLike +
                '}';
    }
}
