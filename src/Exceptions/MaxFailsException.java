package Exceptions;
public class MaxFailsException extends Exception {
    private static final long serialVersionUID = 1L;
    public MaxFailsException() {
        super("Maximum number of failed courses reached.");
    }

    public MaxFailsException(String message) {
        super(message);
    }
}
