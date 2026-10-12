package Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Users.Employee;

import java.io.Serializable;
public class News implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String content;
    private Employee author;
    private Date date;
    private List<String> comments;

    public News(String title, String content, Employee author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = new Date();
        this.comments = new ArrayList<>();
    }

    public String getTitle() { 
    	return title;
    }
    public String getContent() {
    	return content;
    }
    public Employee getAuthor() {
    	return author;
    }
    public Date getDate() {
    	return date;
    }
    public List<String> getComments() { 
    	return comments;
    }

    public void setTitle(String title) { 
    	this.title = title;
    }
    public void setContent(String content) { 
    	this.content = content;
    }

    public void publish() {
        Database.getInstance().getNews().add(this);
        System.out.println("News published: " + title);
    }

    public void edit(String newContent) {
        this.content = newContent;
        System.out.println("News edited: " + title);
    }

    public void delete() {
        Database.getInstance().getNews().remove(this);
        System.out.println("News deleted: " + title);
    }

    public void addComment(String text) {
        comments.add(text);
    }

    @Override
    public String toString() {
        return "[" + date + "] " + title + " by " + author.getName() + "\n" + content;
    }
}
