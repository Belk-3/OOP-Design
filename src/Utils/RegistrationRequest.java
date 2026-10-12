package Utils;
import java.io.Serializable;
import java.util.Date;

import Exceptions.CreditLimitException;
import Researching.RequestStatus;
import Users.Student;

public class RegistrationRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Student student;
    private Course course;
    private RequestStatus status;
    private Date createdAt;

    public RegistrationRequest(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.status = RequestStatus.PENDING;
        this.createdAt = new Date();
    }

    public Student getStudent() { 
    	return student;
    }
    public Course getCourse() {
    	return course;
    }
    public RequestStatus getStatus() { 
    	return status;
    }
    public Date getCreatedAt() {
    	return createdAt;
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
        try {
            student.registerForCourse(course);
        } catch (CreditLimitException e) {
            System.out.println("Cannot approve: " + e.getMessage());
            this.status = RequestStatus.REJECTED;
        }
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
    }

    @Override
    public String toString() {
        return "Request: " + student.getName() + " -> " + course.getName() + " | Status: " + status;
    }
}
