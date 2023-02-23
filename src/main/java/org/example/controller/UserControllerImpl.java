package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserControllerImpl implements UserController{

    private final UserService service;

    public UserControllerImpl() {
        this.service = new UserServiceImpl();
    }

    @Override
    public User getUser(Long id) {

        return service.getUser(id);
    }

    @Override
    public List<User> getUsers() {
        return service.getUsers();
    }

    @Override
    public void deleteUser(Long id) {
        service.deleteUser(id);
    }

    @Override
    public Long create(String name) {
        //валидация
        User user = new User(name, new HashSet<>(), new HashSet<>());

        return service.create(user);
    }

    @Override
    public Long createFullUser(String name, Set<String> citiesWhereLived, Set<String> citiesWhereWorked) {
        User user = new User(name, citiesWhereLived, citiesWhereWorked);
        return service.createFullUser(user);
    }

    @Override
    public Long getCount() {
        return service.getCount();
    }
}
