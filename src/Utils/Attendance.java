package Utils;
import java.util.Date;

import java.util.List;

import Enums.AttendanceStatus;
import Users.Student;

import java.io.Serializable;
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    private Lesson lesson;
    private Student student;
    private AttendanceStatus status;
    private Date date;

    public Attendance(Lesson lesson, Student student, AttendanceStatus status) {
        this.lesson = lesson;
        this.student = student;
        this.status = status;
        this.date = new Date();
    }

    public Lesson getLesson() { 
    	return lesson; 
    }
    public Student getStudent() { 
    	return student; 
    }
    public AttendanceStatus getStatus() { 
    	return status;
    }
    public Date getDate() { 
    	return date; 
    }

    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT || status == AttendanceStatus.LATE;
    }

    public void mark(AttendanceStatus status) {
        this.status = status;
    }

    public static double getRate(Student s, Course c, List<Attendance> list) {
        int total = 0;
        int present = 0;
        for (Attendance a : list) {
            if (a.getStudent().equals(s) && a.getLesson().getCourse().equals(c)) {
                total++;
                if (a.isPresent()) present++;
            }
        }
        if (total == 0) return 0.0;
        return (double) present / total * 100;
    }

    @Override
    public String toString() {
        return student.getName() + " | " + status + " | " + date;
    }
}
