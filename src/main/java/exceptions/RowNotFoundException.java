package exceptions;

public class RowNotFoundException extends Exception {
    public RowNotFoundException(String message) {
        super(message);
    }
}
