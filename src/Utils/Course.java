package Utils;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import Enums.Faculty;
import Enums.Term;
import Users.Student;
import Users.Teacher;

/**
 * This class represents university course.
 * 
 * Course stores teachers, students,
 * lessons and course information.
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private int credits;

    private List<Teacher> teachers;
    private List<Student> enrolledStudents;
    private List<Lesson> lessons;

    private Term targetTerm;
    private Faculty targetFaculty;

    /**
     * Creates course object.
     *
     * @param id course id
     * @param name course name
     * @param credits course credits
     * @param targetTerm target term
     * @param targetFaculty target faculty
     */
    public Course(String id,
                  String name,
                  int credits,
                  Term targetTerm,
                  Faculty targetFaculty) {

        this.id = id;
        this.name = name;
        this.credits = credits;

        this.targetTerm = targetTerm;
        this.targetFaculty = targetFaculty;

        teachers = new ArrayList<>();
        enrolledStudents = new ArrayList<>();
        lessons = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Term getTargetTerm() {
        return targetTerm;
    }

    public Faculty getTargetFaculty() {
        return targetFaculty;
    }

    /**
     * Adds teacher to course.
     *
     * @param t teacher object
     */
    public void addTeacher(Teacher t) {

        if (!teachers.contains(t)) {
            teachers.add(t);
        }
    }

    /**
     * Enrolls student to course.
     *
     * @param s student object
     */
    public void enroll(Student s) {

        if (!enrolledStudents.contains(s)) {
            enrolledStudents.add(s);
        }
    }

    /**
     * Removes student from course.
     *
     * @param s student object
     */
    public void removeStudent(Student s) {

        enrolledStudents.remove(s);
    }

    /**
     * Adds lesson to course.
     *
     * @param l lesson object
     */
    public void addLesson(Lesson l) {

        lessons.add(l);
    }

    /**
     * Returns all students in course.
     *
     * @return list of students
     */
    public List<Student> getStudents() {

        return enrolledStudents;
    }

    /**
     * Returns course information.
     *
     * @return course info
     */
    @Override
    public String toString() {

        return id
                + " | "
                + name
                + " | "
                + credits
                + " credits | "
                + targetTerm
                + " | "
                + targetFaculty;
    }
}