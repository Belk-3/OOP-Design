package Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import Enums.AttendanceStatus;
import Enums.TeacherTitle;
import Researching.ResearcherDecorator;
import Utils.Course;
import Utils.Lesson;
import Utils.Mark;

/**
 * This class represents a teacher in the university system.
 * 
 * Teacher can work with courses, students,
 * marks, attendance and research.
 */
public class Teacher extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private List<Course> courses;
    private double rating;
    private ResearcherDecorator researcherDecorator;

    /**
     * Creates teacher object.
     *
     * @param id teacher id
     * @param username teacher username
     * @param password teacher password
     * @param name teacher name
     * @param surname teacher surname
     * @param salary teacher salary
     * @param department teacher department
     * @param hireDate hire date
     * @param title teacher title
     */
    public Teacher(String id, String username, String password, String name, String surname,
                   double salary, String department, Date hireDate, TeacherTitle title) {

        super(id, username, password, name, surname, salary, department, hireDate);

        this.title = title;
        this.courses = new ArrayList<>();
        this.rating = 0.0;

        if (title == TeacherTitle.PROFESSOR) {
            researcherDecorator = new ResearcherDecorator(this);
        }
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public double getRating() {
        return rating;
    }

    public ResearcherDecorator getResearcherDecorator() {
        return researcherDecorator;
    }

    /**
     * Changes teacher title.
     * If teacher becomes professor, researcher role is added.
     *
     * @param title new title
     */
    public void setTitle(TeacherTitle title) {

        this.title = title;

        if (title == TeacherTitle.PROFESSOR && researcherDecorator == null) {
            researcherDecorator = new ResearcherDecorator(this);
        }
    }

    /**
     * Makes teacher a researcher.
     */
    public void becomeResearcher() {

        if (researcherDecorator == null) {
            researcherDecorator = new ResearcherDecorator(this);
        }
    }

    /**
     * Checks if teacher is researcher.
     *
     * @return true if teacher is researcher
     */
    public boolean isResearcher() {
        return researcherDecorator != null;
    }

    /**
     * Shows teacher courses.
     *
     * @return list of courses
     */
    public List<Course> viewCourses() {
        return courses;
    }

    /**
     * Adds course to teacher.
     *
     * @param c course object
     */
    public void addCourse(Course c) {

        if (!courses.contains(c)) {
            courses.add(c);
            c.addTeacher(this);
        }
    }

    /**
     * Adds mark for student.
     *
     * @param s student
     * @param l lesson
     * @param m mark
     */
    public void putMark(Student s, Lesson l, Mark m) {

        System.out.println("Mark added: " + m);
    }

    /**
     * Shows all students from teacher courses.
     *
     * @return list of students
     */
    public List<Student> viewStudents() {

        List<Student> all = new ArrayList<>();

        for (Course c : courses) {
            for (Student s : c.getEnrolledStudents()) {

                if (!all.contains(s)) {
                    all.add(s);
                }
            }
        }

        return all;
    }

    /**
     * Marks student attendance.
     *
     * @param l lesson
     * @param s student
     * @param status attendance status
     */
    public void markAttendance(Lesson l, Student s, AttendanceStatus status) {

        l.markAttendance(s, status);
    }

    /**
     * Checks if teacher is professor.
     *
     * @return true if professor
     */
    public boolean isProfessor() {
        return title == TeacherTitle.PROFESSOR;
    }

    /**
     * Adds rating to teacher.
     *
     * @param r rating value
     */
    public void addRating(double r) {

        rating = (rating + r) / 2.0;
    }

    /**
     * Returns teacher information.
     *
     * @return teacher info
     */
    @Override
    public String toString() {

        return "Teacher: "
                + getName()
                + " "
                + getSurname()
                + " | "
                + title
                + " | Rating: "
                + rating;
    }
}