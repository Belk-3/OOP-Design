package Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Users.Student;

/**
 * This class represents student marks for course.
 * 
 * It stores attestation scores,
 * exam score and final grade.
 */
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private Course course;

    private double att1;
    private double att2;
    private double finalExam;

    private String comment;

    private int currentAtt;

    private List<Double> currentAttScores;

    /**
     * Creates mark object.
     *
     * @param student student object
     * @param course course object
     * @param att1 first attestation
     * @param att2 second attestation
     * @param finalExam final exam score
     */
    public Mark(Student student,
                Course course,
                double att1,
                double att2,
                double finalExam) {

        this.student = student;
        this.course = course;

        this.att1 = Math.min(att1, 30);
        this.att2 = Math.min(att2, 30);
        this.finalExam = Math.min(finalExam, 40);

        comment = "";

        currentAtt = 1;

        currentAttScores = new ArrayList<>();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getAtt1() {
        return att1;
    }

    public double getAtt2() {
        return att2;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public String getComment() {
        return comment;
    }

    public int getCurrentAtt() {
        return currentAtt;
    }

    public void setAtt1(double v) {
        att1 = Math.min(v, 30);
    }

    public void setAtt2(double v) {
        att2 = Math.min(v, 30);
    }

    public void setFinalExam(double v) {
        finalExam = Math.min(v, 40);
    }

    public void setComment(String c) {
        comment = c;
    }

    /**
     * Adds score for current attestation.
     *
     * @param score lesson score
     * @return true if score added
     */
    public boolean addLessonScore(double score) {

        double current = getCurrentAttScore();

        if (current + score > 30) {

            System.out.println(
                "Cannot add score. Limit is 30 points."
            );

            return false;
        }

        currentAttScores.add(score);

        if (currentAtt == 1) {
            att1 = Math.min(current + score, 30);
        } else {
            att2 = Math.min(current + score, 30);
        }

        return true;
    }

    /**
     * Calculates current attestation score.
     *
     * @return current attestation score
     */
    public double getCurrentAttScore() {

        double sum = 0;

        for (double s : currentAttScores) {
            sum += s;
        }

        return sum;
    }

    /**
     * Switches to next attestation.
     */
    public void nextAttestation() {

        if (currentAtt == 1) {

            currentAtt = 2;

            currentAttScores = new ArrayList<>();

            System.out.println(
                "Now ATT2 started for "
                + course.getName()
            );
        }
    }

    /**
     * Calculates total score.
     *
     * @return total score
     */
    public double calcTotal() {

        return att1 + att2 + finalExam;
    }

    /**
     * Converts score to letter grade.
     *
     * @return letter grade
     */
    public String getLetterGrade() {

        double t = calcTotal();

        if (t >= 95) return "A";
        if (t >= 90) return "A-";
        if (t >= 85) return "B+";
        if (t >= 80) return "B";
        if (t >= 75) return "B-";
        if (t >= 70) return "C+";
        if (t >= 65) return "C";
        if (t >= 60) return "C-";
        if (t >= 55) return "D+";
        if (t >= 50) return "D";

        return "F";
    }

    /**
     * Converts grade to GPA points.
     *
     * @return GPA points
     */
    public double getGradePoint() {

        switch (getLetterGrade()) {

            case "A":
                return 4.0;

            case "A-":
                return 3.67;

            case "B+":
                return 3.33;

            case "B":
                return 3.0;

            case "B-":
                return 2.67;

            case "C+":
                return 2.33;

            case "C":
                return 2.0;

            case "C-":
                return 1.67;

            case "D+":
                return 1.33;

            case "D":
                return 1.0;

            default:
                return 0.0;
        }
    }

    /**
     * Returns lesson object.
     *
     * @return lesson
     */
    public Lesson getLesson() {
        return null;
    }

    /**
     * Returns mark information.
     *
     * @return mark info
     */
    @Override
    public String toString() {

        return "Course: "
                + course.getName()
                + " | ATT"
                + currentAtt
                + ": "
                + String.format("%.1f", getCurrentAttScore())
                + "/30"
                + " | Grade: "
                + getLetterGrade();
    }
}