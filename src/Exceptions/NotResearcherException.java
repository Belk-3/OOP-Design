package Exceptions;
public class NotResearcherException extends Exception {
    private static final long serialVersionUID = 1L;
    public NotResearcherException() {
        super("This user is not a Researcher and cannot join a research project.");
    }

    public NotResearcherException(String message) {
        super(message);
    }
}
