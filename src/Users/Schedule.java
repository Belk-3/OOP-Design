package Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Enums.Term;
import Utils.Database;
import Utils.Lesson;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Lesson> lessons;
    private Term termType;

    public Schedule(Term termType) {
        this.termType = termType;
        this.lessons = new ArrayList<>();
    }

    public List<Lesson> getLessons() { 
    	return lessons;
    }
    public Term getTermType() { 
    	return termType; 
    }

    public void addLesson(Lesson l) {
        if (!lessons.contains(l)) {
            lessons.add(l);
        }
    }

    public void generate() {
        lessons.clear();
        for (Lesson l : Database.getInstance().getLessons()) {
            lessons.add(l);
        }
        System.out.println("Schedule generated for term: " + termType);
    }

    public List<Lesson> getForStudent(Student s) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getCourse().getEnrolledStudents().contains(s)) {
                result.add(l);
            }
        }
        return result;
    }

    public List<Lesson> getForTeacher(Teacher t) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (l.getTeacher().equals(t)) {
                result.add(l);
            }
        }
        return result;
    }

    public boolean checkConflicts() {
        for (int i = 0; i < lessons.size(); i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                Lesson a = lessons.get(i);
                Lesson b = lessons.get(j);
                if (a.getRoom().equals(b.getRoom()) && a.getDateTime().equals(b.getDateTime())) {
                    System.out.println("Conflict: " + a + " vs " + b);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Schedule for " + termType + " | " + lessons.size() + " lessons";
    }
}
