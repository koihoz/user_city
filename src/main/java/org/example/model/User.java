package org.example.model;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class User {
    private final Long id;
    private final String name;
    private final Set<String> citiesWhereLived;
    private final Set<String> citiesWhereWorked;

    //request
    public User(String name, Set<String> citiesWhereLived, Set<String> citiesWhereWorked) {
        this.id = null;
        this.name = name;
        this.citiesWhereLived = citiesWhereLived;
        this.citiesWhereWorked = citiesWhereWorked;
    }

    //response
    public User(Long id, String name, Set<String> citiesWhereLived, Set<String> citiesWhereWorked) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.citiesWhereLived = Objects.requireNonNull(citiesWhereLived);
        this.citiesWhereWorked = Objects.requireNonNull(citiesWhereWorked);
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id); //потом разберем
    }

    public String getName() {
        return name;
    }

    public Set<String> getCitiesWhereLived() {
        return citiesWhereLived;
    }

    public Set<String> getCitiesWhereWorked() {
        return citiesWhereWorked;
    }

    @Override
    public String toString() {
        return "Пользователь " + id + " " + name;
    }
}
