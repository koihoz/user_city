package org.example.ui;

import org.example.controller.UserController;
import org.example.controller.UserControllerImpl;
import org.example.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConsoleUI {

    private final UserController controller;
    private final BufferedReader reader;

    public ConsoleUI() {
        this.controller = new UserControllerImpl();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    //метод для наполения сетов городами
    private Set<String> addCitiesToSet() throws IOException {
        Set<String> cities = new HashSet<>();
        String input;
        while(!(input= reader.readLine()).equals("") ){
            cities.add(input);
            System.out.println("Введите название города");
        }

        return cities;
    }


    public void start(){
        //читать с клавы команды и вызывать методы контролера
        // команды 1,2,3,4,5,
        try  {
            System.out.println("Введите команду.");
            String command = "";
            Long id;
            String name;
            Set<String> citiesWhereLived = new HashSet<>();
            Set<String> citiesWhereWorked = new HashSet<>();
            String answer = "";


            while (!(command = reader.readLine()).equals("x") ){
                switch (command){
                    case "1":
                        System.out.println("Введите id юзера");
                        id = Long.parseLong(reader.readLine());
                        System.out.println(controller.getUser(id));
                        break;
                    case "2":
                        System.out.println("Вывод всех юзеров");
                        List<User> users = controller.getUsers();
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println(users.get(i));
                        }
                        break;
                    case "3":
                        System.out.println("Удалите юзера с id");
                        System.out.println("Введите id юзера");
                        id = Long.parseLong(reader.readLine());
                        controller.deleteUser(id);
                        break;
                    case "4":
                        System.out.println("Добавьте юзера");
                        System.out.println("введите имя");
                        name = reader.readLine();
                        id = controller.create(name);
                        System.out.println("Новый user создан с id " + id);
                        break;
                    case "5":
                        System.out.println("Добавьте юзера с полной информацией");
                        System.out.println("введите имя");
                        name = reader.readLine();
                        System.out.println("введите город где жил");
                        //добавить в hashSet города где жил
                        citiesWhereLived = addCitiesToSet();

                        System.out.println("введите города где работал");
                        //добавить в hashSet города где работал
                        citiesWhereWorked = addCitiesToSet();

                        id = controller.createFullUser(name, citiesWhereLived, citiesWhereWorked );
                        break;
                    case "6":
                        System.out.println("Количество юзеров");
                        System.out.println(controller.getCount());
                        break;
                }
            }

        } catch (IOException e ) {
            e.printStackTrace();
            //по правильному нужно логгирование
        }
    }
}
