package Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.Message;

public abstract class Employee extends User implements Messageable , Serializable {
    private static final long serialVersionUID = 1L;
    private double salary;
    private String department;
    private Date hireDate;
    private List<Message> inbox;

    public Employee(String id, String username, String password, String name, String surname,
                    double salary, String department, Date hireDate) {
        super(id, username, password, name, surname);
        this.salary = salary;
        this.department = department;
        this.hireDate = hireDate;
        this.inbox = new ArrayList<>();
    }

    public double getSalary() { 
    	return salary; 
    }
    public String getDepartment() { 
    	return department; 
    }
    public Date getHireDate() {
    	return hireDate; 
    }
    public void setSalary(double salary) { 
    	this.salary = salary; 
    }

    public double checkSalary() {
        System.out.println("Your salary is: " + salary);
        return salary;
    }

    public void submitComplaint(String text) {
        System.out.println("Complaint submitted by " + getUsername() + ": " + text);
    }

    @Override
    public void sendMessage(Employee to, String msg) {
        Message message = new Message(this, to, msg);
        to.receiveMessage(message);
        System.out.println("Message sent to " + to.getName());
    }

    public void receiveMessage(Message m) {
        inbox.add(m);
    }

    @Override
    public List<Message> getMessages() {
        return inbox;
    }
}
