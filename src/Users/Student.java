package Users;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import Enums.Faculty;
import Exceptions.CreditLimitException;
import Exceptions.LowHIndexException;
import Researching.Researcher;
import Researching.ResearcherDecorator;
import Researching.ResearcherRequest;
import Utils.Attendance;
import Utils.Course;
import Utils.Database;
import Utils.Lesson;
import Utils.Mark;
import Utils.Transcript;

/**
 * This class represents student in university system.
 * 
 * Student can register for courses,
 * view marks, transcript and attendance.
 */
public class Student extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int year;
    private double gpa;
    private int credits;

    private Faculty faculty;

    private Researcher supervisor;

    private List<Course> courses;
    private List<Mark> marks;

    private ResearcherDecorator researcherDecorator;

    // stores retake count for each course
    private java.util.Map<String, Integer> retakeCounts;

    /**
     * Creates student object.
     *
     * @param id student id
     * @param username student username
     * @param password student password
     * @param name student name
     * @param surname student surname
     * @param year year of study
     * @param faculty student faculty
     */
    public Student(String id,
                   String username,
                   String password,
                   String name,
                   String surname,
                   int year,
                   Faculty faculty) {

        super(id, username, password, name, surname);

        this.year = year;
        this.faculty = faculty;

        this.gpa = 0.0;
        this.credits = 0;

        this.courses = new ArrayList<>();
        this.marks = new ArrayList<>();

        this.retakeCounts = new java.util.HashMap<>();
    }

    public int getYear() {
        return year;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public ResearcherDecorator getResearcherDecorator() {
        return researcherDecorator;
    }

    public java.util.Map<String, Integer> getRetakeCounts() {
        return retakeCounts;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Sets research supervisor.
     *
     * @param supervisor research supervisor
     * @throws LowHIndexException if h-index is too low
     */
    public void setSupervisor(Researcher supervisor)
            throws LowHIndexException {

        if (supervisor.getHIndex() < 3) {

            throw new LowHIndexException(
                "Supervisor h-index is too low."
            );
        }

        this.supervisor = supervisor;
    }

    /**
     * Checks if student is researcher.
     *
     * @return true if researcher
     */
    public boolean isResearcher() {
        return researcherDecorator != null;
    }

    /**
     * Activates researcher status.
     */
    public void activateResearcher() {

        if (researcherDecorator == null) {
            researcherDecorator = new ResearcherDecorator(this);
        }
    }

    /**
     * Sends researcher request to manager.
     */
    public void becomeResearcher() {

        if (researcherDecorator != null) {

            System.out.println("Already researcher.");
            return;
        }

        boolean found = false;

        for (User u : Database.getInstance().getAllUsers()) {

            if (u instanceof Manager) {

                ResearcherRequest req =
                        new ResearcherRequest(this);

                ((Manager) u).addResearcherRequest(req);

                found = true;

                System.out.println(
                    "Researcher request sent."
                );

                break;
            }
        }

        if (!found) {
            System.out.println("Manager not found.");
        }
    }

    /**
     * Registers student for course.
     *
     * @param c course object
     * @throws CreditLimitException if credits > 21
     */
    public void registerForCourse(Course c)
            throws CreditLimitException {

        if (credits + c.getCredits() > 21) {

            throw new CreditLimitException(
                "Credit limit exceeded."
            );
        }

        if (!courses.contains(c)) {

            courses.add(c);

            c.enroll(this);

            credits += c.getCredits();

            System.out.println(
                getName() + " enrolled in " + c.getName()
            );

        } else {

            System.out.println(
                "Already enrolled in this course."
            );
        }
    }

    /**
     * Removes course from student.
     *
     * @param c course object
     */
    public void dropCourse(Course c) {

        if (courses.remove(c)) {

            c.removeStudent(this);

            credits -= c.getCredits();

            System.out.println(
                getName() + " dropped " + c.getName()
            );
        }
    }

    /**
     * Adds mark for student.
     *
     * @param m mark object
     */
    public void addMark(Mark m) {

        for (Mark existing : marks) {

            if (existing.getCourse().equals(m.getCourse())
                    && existing.getLetterGrade().equals("F")) {

                String id = m.getCourse().getId();

                retakeCounts.put(
                    id,
                    retakeCounts.getOrDefault(id, 0) + 1
                );

                break;
            }
        }

        marks.removeIf(existing ->
                existing.getCourse().equals(m.getCourse()));

        marks.add(m);

        recalcGpa();
    }

    /**
     * Returns retake count for course.
     *
     * @param c course object
     * @return retake count
     */
    public int getRetakeCount(Course c) {

        return retakeCounts.getOrDefault(c.getId(), 0);
    }

    /**
     * Returns maximum retake count.
     *
     * @return max retake count
     */
    public int getMaxRetakeCount() {

        if (retakeCounts.isEmpty()) {
            return 0;
        }

        return java.util.Collections.max(
                retakeCounts.values()
        );
    }

    /**
     * Recalculates GPA.
     */
    private void recalcGpa() {

        if (marks.isEmpty()) {

            gpa = 0.0;
            return;
        }

        double total = 0;
        int totalCredits = 0;

        for (Mark m : marks) {

            total += m.getGradePoint()
                    * m.getCourse().getCredits();

            totalCredits += m.getCourse().getCredits();
        }

        gpa = totalCredits == 0
                ? 0.0
                : total / totalCredits;
    }

    /**
     * Returns student transcript.
     *
     * @return transcript
     */
    public Transcript getTranscript() {

        Transcript t = new Transcript(this);

        for (Mark m : marks) {
            t.addMark(m);
        }

        t.generate();

        t.sign();

        return t;
    }

    /**
     * Shows current attestation.
     */
    public void viewCurrentAttestation() {

        System.out.println(
            "===== CURRENT ATTESTATION ====="
        );

        if (marks.isEmpty()) {

            System.out.println("No marks yet.");

        } else {

            for (Mark m : marks) {

                System.out.println(
                    m.getCourse().getName()
                    + " | "
                    + m.getCurrentAttScore()
                    + "/30"
                );
            }
        }
    }

    /**
     * Shows full attestation.
     */
    public void viewAttestation() {

        System.out.println(
            "===== FULL ATTESTATION ====="
        );

        if (marks.isEmpty()) {

            System.out.println("No marks yet.");

        } else {

            for (Mark m : marks) {

                System.out.println(
                    "Course: "
                    + m.getCourse().getName()
                );

                System.out.println(
                    "ATT1: " + m.getAtt1()
                    + " | ATT2: " + m.getAtt2()
                    + " | FINAL: " + m.getFinalExam()
                );

                System.out.println(
                    "TOTAL: "
                    + m.calcTotal()
                    + " | Grade: "
                    + m.getLetterGrade()
                );
            }
        }

        System.out.println(
            "GPA: " + String.format("%.2f", gpa)
        );
    }

    /**
     * Shows attendance records.
     */
    public void viewAttendance() {

        System.out.println(
            "===== ATTENDANCE ====="
        );

        List<Lesson> lessons =
                Database.getInstance().getLessons();

        boolean any = false;

        for (Course c : courses) {

            for (Lesson l : lessons) {

                if (l.getCourse().equals(c)) {

                    for (Attendance a : l.getAttendances()) {

                        if (a.getStudent().equals(this)) {

                            System.out.println(
                                c.getName()
                                + " | "
                                + a.getStatus()
                                + " | "
                                + a.getDate()
                            );

                            any = true;
                        }
                    }
                }
            }
        }

        if (!any) {
            System.out.println(
                "No attendance records."
            );
        }
    }

    /**
     * Calculates student debt.
     *
     * @return total debt
     */
    public double calcDebt() {

        double debt = 0;

        for (Mark m : marks) {

            if (m.getLetterGrade().equals("F")) {

                debt += m.getCourse().getCredits()
                        * 35000;
            }
        }

        return debt;
    }

    /**
     * Checks if student has debt.
     *
     * @return true if has debt
     */
    public boolean hasDebt() {

        return calcDebt() > 0;
    }

    /**
     * Shows debts for failed courses.
     */
    public void viewDebts() {

        System.out.println(
            "===== STUDENT DEBTS ====="
        );

        double total = 0;

        boolean any = false;

        for (Mark m : marks) {

            if (m.getLetterGrade().equals("F")) {

                double debt =
                        m.getCourse().getCredits()
                        * 35000;

                System.out.println(
                    m.getCourse().getName()
                    + " | Debt: "
                    + debt + " KZT"
                );

                total += debt;

                any = true;
            }
        }

        if (!any) {

            System.out.println(
                "No debts."
            );

        } else {

            System.out.println(
                "Total debt: "
                + total + " KZT"
            );
        }
    }

    /**
     * Shows all registered courses.
     */
    public void viewJournal() {

        System.out.println(
            "===== COURSES ====="
        );

        if (courses.isEmpty()) {

            System.out.println(
                "No registered courses."
            );
        }

        for (Course c : courses) {
            System.out.println(c);
        }
    }

    /**
     * Rates teacher.
     *
     * @param t teacher
     * @param rating rating value
     */
    public void rateTeacher(Teacher t, int rating) {

        t.addRating(rating);

        System.out.println(
            getName()
            + " rated "
            + t.getName()
        );
    }

    /**
     * Sends request for researcher.
     */
    public void askForResearcher() {

        System.out.println(
            getName()
            + " requested researcher status."
        );
    }

    /**
     * Returns student information.
     *
     * @return student info
     */
    @Override
    public String toString() {

        return "Student: "
                + getName()
                + " "
                + getSurname()
                + " | GPA: "
                + String.format("%.2f", gpa);
    }
}