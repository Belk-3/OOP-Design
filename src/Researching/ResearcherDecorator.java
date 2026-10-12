package Researching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Exceptions.NotResearcherException;
import Users.User;

import java.io.Serializable;
public class ResearcherDecorator implements Researcher , Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private int hIndex;

    public ResearcherDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(c);
        for (ResearchPaper p : sorted) {
            System.out.println(p);
        }
    }

    @Override
    public void addPaper(ResearchPaper p) {
        if (!papers.contains(p)) {
            papers.add(p);
            recalculateHIndex();
        }
    }

    @Override
    public int getHIndex() {
        return hIndex;
    }

    @Override
    public List<ResearchProject> getProjects() {
        return projects;
    }

    @Override
    public int checkCitations() {
        int total = 0;
        for (ResearchPaper p : papers) {
            total += p.getCitations();
        }
        return total;
    }

    public void joinProject(ResearchProject rp) throws NotResearcherException {
        rp.addParticipant(this);
        if (!projects.contains(rp)) {
            projects.add(rp);
        }
    }

    private void recalculateHIndex() {
        List<Integer> citationCounts = new ArrayList<>();
        for (ResearchPaper p : papers) {
            citationCounts.add(p.getCitations());
        }
        citationCounts.sort((a, b) -> b - a);
        int h = 0;
        for (int i = 0; i < citationCounts.size(); i++) {
            if (citationCounts.get(i) >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        this.hIndex = h;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }
}
