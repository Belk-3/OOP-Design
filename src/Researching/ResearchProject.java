package Researching;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Exceptions.NotResearcherException;

import java.io.Serializable;
public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String topic;
    private List<Researcher> participants;
    private List<ResearchPaper> papers;
    private Date startDate;
    private Date endDate;

    public ResearchProject(String topic, Date startDate, Date endDate) {
        this.topic = topic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    public String getTopic() { return topic; }
    public List<Researcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPapers() { return papers; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }

    public void publishPaper(ResearchPaper p) {
        if (!papers.contains(p)) {
            papers.add(p);
        }
    }

    public void addParticipant(Object candidate) throws NotResearcherException {
        if (!(candidate instanceof Researcher)) {
            throw new NotResearcherException("Cannot add participant: " + candidate + " is not a Researcher.");
        }
        Researcher r = (Researcher) candidate;
        if (!participants.contains(r)) {
            participants.add(r);
        }
    }

    @Override
    public String toString() {
        return "Project: " + topic + " [" + startDate + " - " + endDate + "] | Participants: " + participants.size();
    }
}
