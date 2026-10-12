package Exceptions;
public class LowHIndexException extends Exception {
    private static final long serialVersionUID = 1L;
    public LowHIndexException() {
        super("H-index is too low to be assigned as supervisor. Minimum required: 3");
    }

    public LowHIndexException(String message) {
        super(message);
    }
}
