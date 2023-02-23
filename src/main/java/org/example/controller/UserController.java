package org.example.controller;

import org.example.model.User;

import java.util.List;
import java.util.Set;

public interface UserController {
    User getUser(Long id);

    List<User> getUsers();

    void deleteUser(Long id);

    Long create(String name);

    Long createFullUser(String name, Set<String> citiesWhereLived, Set<String> citiesWhereWorked);

    Long getCount();


}
