package Researching;
import java.util.Comparator;
import java.util.List;

public interface Researcher {
    void printPapers(Comparator<ResearchPaper> c);
    void addPaper(ResearchPaper p);
    int getHIndex();
    List<ResearchProject> getProjects();
    int checkCitations();
}
