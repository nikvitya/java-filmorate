package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class RatingMpa {
    private Integer id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingMpa)) return false;
        RatingMpa ratingMpa = (RatingMpa) o;
        return Objects.equals(id, ratingMpa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RatingMpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
