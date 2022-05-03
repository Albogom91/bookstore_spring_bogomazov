package com.belhard.bookstore.controller;

import main.java.com.belhard.bookstore.dao.UserDao;
import main.java.com.belhard.bookstore.dao.impl.UserDaoJdbcImpl;
import main.java.com.belhard.bookstore.service.UserService;
import main.java.com.belhard.bookstore.service.dto.UserDto;
import main.java.com.belhard.bookstore.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class AppUser {
    private static final int I = 1;
    public static final String INFO = "Please, enter \"all\" if you want to see information about all users;\n" +
            "please, enter \"get #\" where # is the id of the user about whom you want to see full information;\n" +
            "please, enter \"delete #\" where # is the id of the user whom you want to delete;\n" +
            "please, enter \"create\" if you want to create new user by providing necessary information;\n" +
            "please, enter \"update #\" where # is the id of the user whom you want to update;\n" +
            "please, enter \"exit\" if you want to finish your work with this application.\n";
    private static final UserDao USER_DAO = new UserDaoJdbcImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();


    public static void main(String ... args) {
        Scanner scanner = new Scanner(System.in);
        chooseMainMenuOption(scanner);
        scanner.close();

        System.out.print("Current number of users is: ");
        System.out.println(USER_SERVICE.countAll());
        System.out.println(USER_SERVICE.validate("test1111@gmail.com", "1111"));

    }

    public static void chooseMainMenuOption(Scanner scanner) {
        while (true) {
            String divider = "-----------------------------------";
            System.out.print(INFO);
            System.out.println(divider);
            String input = scanner.nextLine();
            String[] options = input.split(" ");
            switch (options[0]) {
                case "all": {
                    List<UserDto> userDtos = USER_SERVICE.getAll();
                    for (UserDto userDto : userDtos) {
                        System.out.println(userDto);
                    }
                    System.out.println(divider);
                    break;
                }
                case "get": {
                    UserDto userDto = USER_SERVICE.getById(Long.parseLong(options[1]));
                    System.out.println(userDto);
                    System.out.println(divider);
                    break;
                }
                case "delete": {
                    USER_SERVICE.delete(Long.parseLong(options[1]));
                    System.out.println(divider);
                    break;
                }
                case "create": {
                    UserDto userDto = new UserDto();
                    System.out.print("Please, enter first name of the new user: ");
                    userDto.setFirstName(scanner.nextLine());
                    System.out.print("Please, enter last name of the new user: ");
                    userDto.setLastName(scanner.nextLine());
                    System.out.print("Please, enter email of the new user: ");
                    userDto.setEmail(scanner.nextLine());
                    System.out.print("Please, enter password of the new user: ");
                    userDto.setPassword(scanner.nextLine());
                    System.out.print("Please, enter role (admin, manager or customer) of the new user: ");
                    userDto.setRoleDto(UserDto.RoleDto.valueOf(scanner.nextLine().toUpperCase()));
                    USER_SERVICE.create(userDto);
                    System.out.println(divider);
                    break;
                }
                case "update": {
                    UserDto userDto = USER_SERVICE.getById(Long.parseLong(options[1]));
                    System.out.print("Please, enter new first name of the user you wish to update: ");
                    userDto.setFirstName(scanner.nextLine());
                    System.out.print("Please, enter new last name of the user you wish to update: ");
                    userDto.setLastName(scanner.nextLine());
                    System.out.print("Please, enter new email of the user you wish to update: ");
                    userDto.setEmail(scanner.nextLine());
                    System.out.print("Please, enter new password of the user you wish to update: ");
                    userDto.setPassword(scanner.nextLine());
                    System.out.print("Please, enter new role (admin, manager or customer) of the user you wish to update: ");
                    userDto.setRoleDto(UserDto.RoleDto.valueOf(scanner.nextLine().toUpperCase()));
                    USER_SERVICE.update(userDto);
                    System.out.println(divider);
                    break;
                }
                case "exit": {
                    return;
                }
                default: {
                    System.out.println("Invalid input! Please, try again!");
                    System.out.println(divider);
                    break;
                }
            }
        }
    }
}
