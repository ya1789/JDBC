package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Афанасий", "Афонов", (byte) 20);
        userService.saveUser("Борис", "Буданов", (byte) 25);
        userService.saveUser("Василий", "Васанов", (byte) 31);
        userService.saveUser("Григорий", "Гагарин", (byte) 38);

        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        // реализуйте алгоритм здесь

    }
}
