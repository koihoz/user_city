package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService{

    private final UserDao repo;

    public UserServiceImpl() {
        this.repo = new UserDaoImpl();
    }

    @Override
    public User getUser(Long id) {
        return repo.getUser(id);
    }

    @Override
    public List<User> getUsers() {
        return repo.getUsers();
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteUser(id);
    }

    @Override
    public Long create(User user) {
        return repo.create(user);
    }

    @Override
    public Long createFullUser(User user) {
        return repo.createFullUser(user);
    }

    @Override
    public Long getCount() {
        return repo.getCount();
    }
}
