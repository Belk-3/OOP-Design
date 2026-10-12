package Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import Users.Student;

/**
 * This class represents student transcript.
 * 
 * Transcript stores student's marks,
 * GPA, generation date and signature status.
 */
public class Transcript implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private List<Mark> marks;
    private Date generatedAt;
    private boolean signed;

    /**
     * Creates transcript for student.
     *
     * @param student student object
     */
    public Transcript(Student student) {
        this.student = student;
        this.marks = new ArrayList<>();
        this.signed = false;
    }

    public Student getStudent() {
        return student;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public boolean isSigned() {
        return signed;
    }

    /**
     * Adds mark to transcript.
     *
     * @param m mark object
     */
    public void addMark(Mark m) {
        marks.add(m);
    }

    /**
     * Generates transcript date.
     */
    public void generate() {
        generatedAt = new Date();
    }

    /**
     * Calculates GPA from all marks.
     *
     * @return calculated GPA
     */
    public double calcGPA() {

        if (marks.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        int credits = 0;

        for (Mark m : marks) {
            sum += m.getGradePoint() * m.getCourse().getCredits();
            credits += m.getCourse().getCredits();
        }

        if (credits == 0) {
            return 0.0;
        }

        return sum / credits;
    }

    /**
     * Prints transcript information.
     */
    public void print() {

        System.out.println("========== TRANSCRIPT ==========");
        System.out.println("Student: " + student.getName() + " " + student.getSurname());
        System.out.println("GPA: " + String.format("%.2f / 4.00", calcGPA()));
        System.out.println("--------------------------------");

        if (marks.isEmpty()) {

            System.out.println("No marks recorded.");

        } else {

            for (Mark m : marks) {
                System.out.println(
                    m.getCourse().getName()
                    + ": "
                    + String.format("%.0f", m.calcTotal())
                    + "/100 ("
                    + m.getLetterGrade()
                    + ")"
                );
            }
        }

        System.out.println("--------------------------------");

        if (generatedAt != null) {
            System.out.println("Generated: " + generatedAt);
        } else {
            System.out.println("Generated: -");
        }

        System.out.println("Signed: " + (signed ? "Yes" : "No"));
        System.out.println("================================");
    }

    /**
     * Signs transcript.
     */
    public void sign() {
        signed = true;
    }
}