package Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Users.Admin;
import Users.Manager;
import Users.Student;
import Users.Teacher;
import Users.User;

public class AuthService {

    private Map<String, User> sessions;

    public AuthService() {
        sessions = new HashMap<>();
    }

    public User authenticate(String login, String password) {

        for (User u : Database.getInstance().getAllUsers()) {

            if (u.getUsername().equals(login) && u.login(password)) {

                String token = UUID.randomUUID().toString();

                sessions.put(token, u);

                Logger.getInstance().log("Login successful", u);

                System.out.println(
                    "Logged in as: "
                    + u.getUsername()
                    + " | Token: "
                    + token
                );

                return u;
            }
        }

        System.out.println("Wrong login or password.");

        return null;
    }

    public void logout(String token) {

        User u = sessions.remove(token);

        if (u != null) {

            Logger.getInstance().log("Logout", u);

            System.out.println(
                u.getUsername()
                + " logged out."
            );
        }
    }

    public boolean isAuthorized(User user, String role) {

        if (user == null) {
            return false;
        }

        switch (role) {

            case "ADMIN":
                return user instanceof Admin;

            case "TEACHER":
                return user instanceof Teacher;

            case "STUDENT":
                return user instanceof Student;

            case "MANAGER":
                return user instanceof Manager;

            default:
                return false;
        }
    }

    public Map<String, User> getSessions() {
        return sessions;
    }
}