package Users;

import java.io.Serializable;
import java.util.List;

import Utils.Database;
import Utils.News;

/**
 * This class represents a basic user of the university system.
 * 
 * All users have personal information,
 * login functionality and access to news.
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String passwordHash;
    private String name;
    private String surname;

    /**
     * Creates user object.
     *
     * @param id user id
     * @param username username
     * @param password password
     * @param name first name
     * @param surname last name
     */
    public User(String id, String username, String password,
                String name, String surname) {

        this.id = id;
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Checks user password during login.
     *
     * @param enteredPassword entered password
     * @return true if password is correct
     */
    public boolean login(String enteredPassword) {

        return hashPassword(enteredPassword)
                .equals(passwordHash);
    }

    /**
     * Logs user out of the system.
     */
    public void logout() {

        System.out.println(username + " logged out.");
    }

    /**
     * Changes current password.
     *
     * @param newPassword new password
     */
    public void changePassword(String newPassword) {

        passwordHash = hashPassword(newPassword);

        System.out.println("Password changed.");
    }

    /**
     * Shows all news in the system.
     */
    public void viewNews() {

        List<News> newsList = Database.getInstance().getNews();

        for (News n : newsList) {
            System.out.println(n);
        }
    }

    /**
     * Creates report.
     */
    public void makeReport() {

        System.out.println("Report created by " + username);
    }

    /**
     * Converts password into hash value.
     *
     * @param p password
     * @return hashed password
     */
    protected String hashPassword(String p) {

        int hash = 0;

        for (char c : p.toCharArray()) {
            hash = 31 * hash + c;
        }

        return Integer.toHexString(hash);
    }

    /**
     * Returns user information.
     *
     * @return user info
     */
    @Override
    public String toString() {

        return name + " " + surname
                + " (" + username + ")";
    }
}