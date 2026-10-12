package Researching;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import java.io.Serializable;
public class ResearchPaper implements Comparable<ResearchPaper> , Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private List<String> authors;
    private Date date;
    private int pages;
    private int citations;
    private String journal;
    private String doi;

    public ResearchPaper(String title, List<String> authors, Date date, int pages, int citations, String journal, String doi) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.date = date;
        this.pages = pages;
        this.citations = citations;
        this.journal = journal;
        this.doi = doi;
    }

    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public Date getDate() { return date; }
    public int getPages() { return pages; }
    public int getCitations() { return citations; }
    public String getJournal() { return journal; }
    public String getDoi() { return doi; }

    public void setCitations(int citations) { this.citations = citations; }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + authors + " | Citations: " + citations + " | Journal: " + journal + " | Pages: " + pages;
    }
}
