package Users;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import Enums.ManagerType;
import Exceptions.LowHIndexException;
import Researching.Researcher;
import Researching.ResearcherRequest;
import Utils.Course;
import Utils.Database;
import Utils.Logger;
import Utils.News;
import Utils.RegistrationRequest;
import Utils.Report;

/**
 * This class represents a manager in the university system.
 * 
 * Manager works with course registration,
 * researcher requests, reports and students.
 */
public class Manager extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private ManagerType type;

    private List<RegistrationRequest> requests;
    private List<ResearcherRequest> researcherRequests;

    /**
     * Creates manager object.
     *
     * @param id manager id
     * @param username manager username
     * @param password manager password
     * @param name manager name
     * @param surname manager surname
     * @param salary manager salary
     * @param department manager department
     * @param hireDate hire date
     * @param type manager type
     */
    public Manager(String id,
                   String username,
                   String password,
                   String name,
                   String surname,
                   double salary,
                   String department,
                   Date hireDate,
                   ManagerType type) {

        super(id, username, password,
              name, surname,
              salary, department, hireDate);

        this.type = type;

        requests = new ArrayList<>();
        researcherRequests = new ArrayList<>();
    }

    public ManagerType getType() {
        return type;
    }

    public List<RegistrationRequest> getRequests() {
        return requests;
    }

    public List<ResearcherRequest> getResearcherRequests() {
        return researcherRequests;
    }

    /**
     * Adds researcher request.
     *
     * @param r researcher request
     */
    public void addResearcherRequest(ResearcherRequest r) {

        researcherRequests.add(r);

        System.out.println(
            "Researcher request from "
            + r.getStudent().getName()
            + " added."
        );
    }

    /**
     * Approves registration request.
     *
     * @param r registration request
     */
    public void approveRegistration(RegistrationRequest r) {

        r.approve();

        Logger.getInstance().log(
            "Approved registration for "
            + r.getCourse().getName(),
            this
        );
    }

    /**
     * Rejects registration request.
     *
     * @param r registration request
     */
    public void rejectRegistration(RegistrationRequest r) {

        r.reject();

        Logger.getInstance().log(
            "Rejected registration for "
            + r.getCourse().getName(),
            this
        );
    }

    /**
     * Approves researcher request.
     *
     * @param r researcher request
     */
    public void approveResearcherRequest(ResearcherRequest r) {

        r.approve();

        Logger.getInstance().log(
            "Approved researcher request for "
            + r.getStudent().getName(),
            this
        );
    }

    /**
     * Rejects researcher request.
     *
     * @param r researcher request
     */
    public void rejectResearcherRequest(ResearcherRequest r) {

        r.reject();

        Logger.getInstance().log(
            "Rejected researcher request for "
            + r.getStudent().getName(),
            this
        );
    }

    /**
     * Adds course to database.
     *
     * @param c course
     */
    public void addCourseForRegistration(Course c) {

        for (Course existing : Database.getInstance().getCourses()) {

            if (existing.getName().equalsIgnoreCase(c.getName())) {

                System.out.println(
                    "Course with this name already exists."
                );

                return;
            }
        }

        Database.getInstance().addCourse(c);

        System.out.println(
            "Course added: " + c.getName()
        );
    }

    /**
     * Assigns teacher to course.
     *
     * @param t teacher
     * @param c course
     */
    public void assignTeacher(Teacher t, Course c) {

        c.addTeacher(t);
        t.addCourse(c);

        System.out.println(
            t.getName()
            + " assigned to "
            + c.getName()
        );
    }

    /**
     * Shows all reports.
     */
    public void checkReport() {

        for (Report r : Database.getInstance().getReports()) {
            System.out.println(r);
        }
    }

    /**
     * Shows all news.
     */
    public void manageNews() {

        System.out.println("News:");

        for (News n : Database.getInstance().getNews()) {
            System.out.println(n);
        }
    }

    /**
     * Shows students sorted by GPA.
     */
    public void viewStudentsSortedByGPA() {

        List<Student> students = new ArrayList<>();

        for (User u : Database.getInstance().getAllUsers()) {

            if (u instanceof Student) {
                students.add((Student) u);
            }
        }

        students.sort(
            Comparator.comparingDouble(Student::getGpa).reversed()
        );

        System.out.println("=== Students by GPA ===");

        for (Student s : students) {

            System.out.println(
                s.getName()
                + " "
                + s.getSurname()
                + " | GPA: "
                + s.getGpa()
            );
        }
    }

    /**
     * Removes employee from system.
     *
     * @param e employee
     */
    public void fireEmployee(Employee e) {

        Database.getInstance()
                .getUsers()
                .remove(e.getId());

        System.out.println(
            e.getName()
            + " "
            + e.getSurname()
            + " was removed from system."
        );

        Logger.getInstance().log(
            "Fired employee: "
            + e.getName(),
            this
        );
    }

    /**
     * Deducts student with too many retakes.
     *
     * @param s student
     */
    public void deductStudent(Student s) {

        int maxRetakes = s.getMaxRetakeCount();

        if (maxRetakes <= 3) {

            System.out.println(
                "Student cannot be deducted."
            );

            return;
        }

        List<Course> courses =
            new ArrayList<>(s.getCourses());

        for (Course c : courses) {
            c.removeStudent(s);
        }

        Database.getInstance()
                .getUsers()
                .remove(s.getId());

        System.out.println(
            s.getName()
            + " was deducted from university."
        );

        Logger.getInstance().log(
            "Deducted student: "
            + s.getName(),
            this
        );
    }

    /**
     * Shows all registration requests.
     */
    public void viewRequests() {

        for (RegistrationRequest r : requests) {
            System.out.println(r);
        }
    }

    /**
     * Adds registration request.
     *
     * @param r registration request
     */
    public void addRequest(RegistrationRequest r) {
        requests.add(r);
    }

    /**
     * Signs report.
     *
     * @param r report
     */
    public void signReport(Report r) {
        r.sign(this);
    }

    /**
     * Assigns supervisor to student.
     *
     * @param s student
     * @param r researcher
     * @throws LowHIndexException low h-index exception
     */
    public void assignResearcher(Student s,
                                 Researcher r)
                                 throws LowHIndexException {

        s.setSupervisor(r);

        System.out.println(
            "Supervisor assigned to "
            + s.getName()
        );
    }

    /**
     * Returns manager information.
     *
     * @return manager info
     */
    @Override
    public String toString() {

        return "Manager: "
                + getName()
                + " "
                + getSurname()
                + " | "
                + type;
    }
}