package org.example;

import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.example.ui.ConsoleUI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        //ConsoleUI session = new ConsoleUI();
        //session.start();
        Set<String> diffCities = new HashSet<>();
        diffCities.add("London");
        diffCities.add("Manchester");
        diffCities.add("Newcastle");

        UserDaoImpl udi = new UserDaoImpl();
        udi.fillCities(diffCities,"lived", 10L);

    }

}