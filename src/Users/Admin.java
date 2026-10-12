package Users;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import Utils.Database;
import Utils.Logger;
import Utils.Report;

/**
 * This class represents an admin in the university system.
 * 
 * Admin can manage users, check logs,
 * and generate reports.
 */
public class Admin extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Logger logFile;

    /**
     * Creates an admin object.
     * 
     * @param id admin id
     * @param username admin username
     * @param password admin password
     * @param name admin name
     * @param surname admin surname
     * @param salary admin salary
     * @param department admin department
     * @param hireDate date of employment
     */
    public Admin(String id,
                 String username,
                 String password,
                 String name,
                 String surname,
                 double salary,
                 String department,
                 Date hireDate) {

        super(id, username, password,
              name, surname,
              salary, department, hireDate);

        logFile = Logger.getInstance();
    }

    /**
     * Shows all users in the system.
     */
    public void manageUsers() {

        System.out.println("=== Users ===");

        for (User u : Database.getInstance().getAllUsers()) {
            System.out.println(u);
        }
    }

    /**
     * Adds a new user to database.
     * 
     * @param u user object
     */
    public void addUser(User u) {

        Database.getInstance().save(u);

        logFile.log(
            "Added user: " + u.getUsername(), this
        );

        System.out.println(
            "User added successfully"
        );
    }

    /**
     * Removes user from database.
     * 
     * @param u user object
     */
    public void removeUser(User u) {

        Database.getInstance()
                .getUsers()
                .remove(u.getId());

        logFile.log(
            "Removed user: " + u.getUsername(),this
        );

        System.out.println(
            "User removed successfully"
        );
    }

    /**
     * Updates user information.
     * 
     * @param u updated user
     */
    public void updateUser(User u) {

        Database.getInstance().save(u);

        logFile.log(
            "Updated user: " + u.getUsername(), this
        );

        System.out.println(
            "User updated successfully"
        );
    }

    /**
     * Prints all system logs.
     */
    public void viewLogFiles() {

        List<String> logs = logFile.getLogs();

        System.out.println("=== Logs ===");

        for (String s : logs) {
            System.out.println(s);
        }
    }

    /**
     * Finds user by id.
     * 
     * @param id user id
     * @return found user
     */
    public User viewUser(String id) {

        return (User)
                Database.getInstance().load(id);
    }

    /**
     * Generates admin report.
     */
    public void generateReport() {

        Report r = new Report(
            "ADMIN_REPORT", this
        );

        r.generate();

        Database.getInstance().addReport(r);
    }

    /**
     * Returns admin information.
     * 
     * @return admin string
     */
    @Override
    public String toString() {

        return "Admin: "
             + getName()
             + " "
             + getSurname();
    }
}