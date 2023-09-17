package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
//        userService.dropUsersTable();
//        userService.createUsersTable();

        userService.saveUser("Fil", "Pl", (byte) 40);
//        userService.saveUser("Ksu", "Pl", (byte) 40);

//        userService.removeUserById(6L);
//        userService.getAllUsers();
//        userService.cleanUsersTable();
//        userService.getAllUsers();
    }
}
