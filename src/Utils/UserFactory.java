package Utils;

import java.util.Date;
import java.util.UUID;

import Enums.Faculty;
import Enums.ManagerType;
import Enums.TeacherTitle;
import Users.Admin;
import Users.FinanceManager;
import Users.Manager;
import Users.Student;
import Users.Teacher;
import Users.User;

public class UserFactory {
    public static User createUser(String role, String username, String password, String name, String surname) {
        String id = UUID.randomUUID().toString();
        switch (role.toUpperCase()) {
            case "STUDENT":
                return new Student(id, username, password, name, surname, 1, Faculty.SITE);
            case "TEACHER":
                return new Teacher(id, username, password, name, surname, 1500000, "General", new Date(), TeacherTitle.LECTOR);
            case "ADMIN":
                return new Admin(id, username, password, name, surname, 1000000, "Administration", new Date());
            case "MANAGER":
                return new Manager(id, username, password, name, surname, 1800000, "Management", new Date(), ManagerType.DEPARTMENT);
            case "FINANCE":
                return new FinanceManager(id, username, password, name, surname, 1700000, "Finance", new Date());
            default:
                System.out.println("Unknown role: " + role);
                return null;
        }
    }
}
