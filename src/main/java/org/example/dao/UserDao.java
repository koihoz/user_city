package org.example.dao;

import org.example.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User getUser(Long id);

    List<User> getUsers();

    void deleteUser(Long id);

    Long create(User user);

    Long createFullUser(User user);

    Long getCount();
}
