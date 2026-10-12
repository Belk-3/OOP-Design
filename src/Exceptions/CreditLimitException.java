package Exceptions;
public class CreditLimitException extends Exception {
    private static final long serialVersionUID = 1L;
    public CreditLimitException() {
        super("Credit limit exceeded. Cannot register for more courses.");
    }

    public CreditLimitException(String message) {
        super(message);
    }
}
