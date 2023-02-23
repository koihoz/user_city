package org.example.dao;
import org.example.config.PostgreConnection;
import org.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/** check*/
public class UserDaoImpl implements UserDao{


    private PostgreConnection postgreConnection;

    // что мы тут делаем?
    public UserDaoImpl(){
        this.postgreConnection = new PostgreConnection();
    }


    @Override
    public User getUser(Long id) {
        User user = null;
        try {
            //make query to describe the command
            String query = "select * from users where users.id = " + id;
            //make object
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user = new User(id, resultSet.getString("name"), new HashSet<>(), new HashSet<>());
            }
        } catch ( SQLException e ) {
            throw new RuntimeException("get user fail");
        }

        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "select * from users";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getLong("id"), resultSet.getString("name"), new HashSet<>(), new HashSet<>());
                users.add(user);
            }
            //statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("get users fail");
        }

        return users;
    }

    @Override
    public void deleteUser(Long id) {
        try {
            String query = "delete from users where id = " + id;
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException(" no affected rows have found");
            } else {
                System.out.println("delete successful");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(" delete user fail");
        }
    }

    @Override
    public Long create(User user) {
        long id;
        String username = user.getName();
        //Set<String> citiesWhereLived = user.getCitiesWhereLived();
        //Set<String> citiesWhereWorked = user.getCitiesWhereWorked();
        try {
            String query = "insert into users (name) values ('" + username + "')";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException(" no affected rows have found");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if ( generatedKeys.next() ) {
                id = generatedKeys.getLong(1);
                    System.out.println("create user success, id = " + id);
                } else {
                    throw  new RuntimeException("didn't get an Id");
                }

            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(" add user fail");
        }
        return id;
    }

    @Override
    public Long createFullUser(User user) {
        long id;
        String username = user.getName();
        Set<String> citiesWhereLived = user.getCitiesWhereLived();
        Set<String> citiesWhereWorked = user.getCitiesWhereWorked();

        try {
            String query = "insert into users (name) values ('" + username + "')";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException(" no affected rows have found");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if ( generatedKeys.next() ) {
                    id = generatedKeys.getLong(1);
                    System.out.println("create user success, id = " + id);
                } else {
                    throw  new RuntimeException("didn't get an Id");
                }
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(" add user fail");
        }
        //добавил вызов функции fillCities
        fillCities(citiesWhereLived, "lived", id);
        fillCities(citiesWhereWorked, "worked", id);

        return id;
    }

    //проверяем есть ли город в табличке Cities- пишем метод getCityId
    //каждый город из set проверяем на наличие в таблице cities.
    // если город в таблице есть, то взвращаем его id
    // если города нет, то вызываем метод createCities
    //получили id юзера , id города
    // добавляем все города из сета lived в табличку lived  и все города из сета worked в табличку worked.

    public void fillCities(Set<String> cities, String tableName, Long id){
        for (String city: cities) {
            Long cityId = getCityId(city);
            if(cityId == 0){
                cityId = createCity(city);
            }
           String query = "insert into " + tableName + " (city_id, user_id) values (" + cityId +", " + id +")";
            try {
                PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
                int affectedRows = statement.executeUpdate();
                if(affectedRows == 0){
                    throw new RuntimeException("no affected rows");
                }  else {
                    System.out.println("affected rows have found");
                }
             statement.close();
            } catch (SQLException e) {
                throw new RuntimeException("fill cities fail");
            }
        }
    }

    private long createCity(String city){
        Long id = 0L;

        try {
            String query = "insert into cities (name) values ('" + city + "')";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            if(affectedRows == 0) {
                throw new RuntimeException("no affected rows");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    id = generatedKeys.getLong(1);
                    System.out.println("create city success, id = " + id);
                } else {
                    throw new RuntimeException("didn't get an Id");
                }
            }
        return id;
        } catch (SQLException e) {
            throw new RuntimeException(" create city fail");
        }
    }

    public long getCityId(String city){
        long id = 0L;
        try {
            String query = "select id from cities where name = '" + city + "'";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getLong("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(" get user fail");
        }
    }


    @Override
    public Long getCount() {
        Long count = 0L;
        try{
            String query = "select count(*) from users";
            PreparedStatement statement = postgreConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("didnt get a Count");
        }

        return count;
    }
}

