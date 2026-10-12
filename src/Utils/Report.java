package Utils;
import java.io.File;
import java.util.Date;

import Users.Employee;
import Users.Student;
import Users.User;

import java.io.Serializable;
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private String content;
    private Employee author;
    private Employee signedBy;
    private Date date;

    public Report(String type, Employee author) {
        this.type = type;
        this.author = author;
        this.date = new Date();
        this.content = "";
    }

    public String getType() { 
    	return type;
    }
    public String getContent() { 
    	return content; 
    }
    public Employee getAuthor() { 
    	return author; 
    }
    public Employee getSignedBy() {
    	return signedBy; 
    }
    public Date getDate() { 
    	return date; 
    }

    public void generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Report: ").append(type).append(" ===\n");
        sb.append("Author: ").append(author.getName()).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("--- Statistics ---\n");
        for (User u : Database.getInstance().getAllUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                sb.append(s.getName()).append(" GPA: ").append(s.getGpa()).append("\n");
            }
        }
        this.content = sb.toString();
        System.out.println("Report generated.");
    }

    public void sign(Employee e) {
        this.signedBy = e;
        System.out.println("Report signed by " + e.getName());
    }

    public File export() {
        System.out.println("Exporting report to file...");
        return new File("report_" + type + "_" + date.getTime() + ".txt");
    }

    @Override
    public String toString() {
        return "Report[" + type + "] by " + author.getName() + " on " + date;
    }
}
