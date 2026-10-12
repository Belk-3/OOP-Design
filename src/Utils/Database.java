package Utils;

import java.io.*;
import java.util.*;

import Users.User;

public class Database implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Database instance;

    private Map<String, User> users;
    private List<Course> courses;
    private List<Lesson> lessons;
    private List<News> news;
    private List<Report> reports;

    private Database() {
        users = new HashMap<>();
        courses = new ArrayList<>();
        lessons = new ArrayList<>();
        news = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public static Database getInstance() {

        if (instance == null) {
            instance = loadFromFile();

            if (instance == null) {
                instance = new Database();
            }
        }

        return instance;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<News> getNews() {
        return news;
    }

    public List<Report> getReports() {
        return reports;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void save(User u) {
        users.put(u.getId(), u);
    }

    public Object load(String id) {
        return users.get(id);
    }

    public boolean addCourse(Course c) {

        for (Course existing : courses) {

            if (existing.getName().equalsIgnoreCase(c.getName())) {
                System.out.println("Course already exists: " + c.getName());
                return false;
            }

            if (existing.getId().equals(c.getId())) {
                System.out.println("Course ID already exists: " + c.getId());
                return false;
            }
        }

        courses.add(c);
        return true;
    }

    public void addLesson(Lesson l) {
        lessons.add(l);
    }

    public void addReport(Report r) {
        reports.add(r);
    }

    public void serialize() {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("database.ser"))) {

            oos.writeObject(this);
            System.out.println("Database saved.");

        } catch (IOException e) {
            System.out.println("Could not save database: " + e.getMessage());
        }
    }

    private static Database loadFromFile() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("database.ser"))) {

            return (Database) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}