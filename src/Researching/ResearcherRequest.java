package Researching;
import java.io.Serializable;
import java.util.Date;

import Users.Student;

public class ResearcherRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private Student student;
    private RequestStatus status;
    private Date createdAt;

    public ResearcherRequest(Student student) {
        this.student = student;
        this.status = RequestStatus.PENDING;
        this.createdAt = new Date();
    }

    public Student getStudent() { return student; }
    public RequestStatus getStatus() { return status; }
    public Date getCreatedAt() { return createdAt; }

    public void approve() {
        this.status = RequestStatus.APPROVED;
        student.activateResearcher();
        System.out.println("✅ Researcher request approved for " + student.getName());
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
        System.out.println("❌ Researcher request rejected for " + student.getName());
    }

    @Override
    public String toString() {
        return "ResearcherRequest: " + student.getName() + " " + student.getSurname()
             + " | Status: " + status + " | " + createdAt;
    }
}
