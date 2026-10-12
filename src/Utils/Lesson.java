package Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import Enums.AttendanceStatus;
import Enums.LessonType;
import Users.Student;
import Users.Teacher;

/**
 * This class represents lesson in university system.
 * 
 * Lesson stores information about course,
 * teacher, classroom and attendance.
 */
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    private LessonType type;
    private Course course;
    private Teacher teacher;

    private LocalDateTime dateTime;

    private String room;
    private int duration;

    private List<Attendance> attendances;

    /**
     * Creates lesson object.
     *
     * @param type lesson type
     * @param course lesson course
     * @param teacher lesson teacher
     * @param dateTime lesson date and time
     * @param room classroom
     * @param duration lesson duration
     */
    public Lesson(LessonType type,
                  Course course,
                  Teacher teacher,
                  LocalDateTime dateTime,
                  String room,
                  int duration) {

        this.type = type;
        this.course = course;
        this.teacher = teacher;

        this.dateTime = dateTime;

        this.room = room;
        this.duration = duration;

        attendances = new ArrayList<>();
    }

    public LessonType getType() {
        return type;
    }

    public Course getCourse() {
        return course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRoom() {
        return room;
    }

    public int getDuration() {
        return duration;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    /**
     * Changes lesson date and time.
     *
     * @param newDateTime new lesson date
     */
    public void reschedule(LocalDateTime newDateTime) {

        this.dateTime = newDateTime;

        System.out.println(
            "Lesson moved to "
            + newDateTime
        );
    }

    /**
     * Marks attendance for student.
     *
     * @param s student
     * @param status attendance status
     */
    public void markAttendance(Student s, AttendanceStatus status) {

        for (Attendance a : attendances) {

            if (a.getStudent().equals(s)) {
                a.mark(status);
                return;
            }
        }

        attendances.add(
            new Attendance(this, s, status)
        );
    }

    /**
     * Calculates attendance percentage.
     *
     * @return attendance rate
     */
    public double getAttendanceRate() {

        if (attendances.isEmpty()) {
            return 0.0;
        }

        long present = 0;

        for (Attendance a : attendances) {

            if (a.isPresent()) {
                present++;
            }
        }

        return (double) present
                / attendances.size()
                * 100;
    }

    /**
     * Returns lesson information.
     *
     * @return lesson info
     */
    @Override
    public String toString() {

        return type
                + " | "
                + course.getName()
                + " | Room: "
                + room
                + " | "
                + dateTime;
    }
}