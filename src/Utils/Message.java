package Utils;
import java.util.Date;

import Users.Employee;

import java.io.Serializable;
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private Employee sender;
    private Employee receiver;
    private String content;
    private Date sentAt;

    public Message(Employee sender, Employee receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sentAt = new Date();
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Date getSentAt() {
        return sentAt;
    }

    @Override
    public String toString() {
        return "From: " + sender.getName() + " To: " + receiver.getName() + " | " + content + " [" + sentAt + "]";
    }
}
