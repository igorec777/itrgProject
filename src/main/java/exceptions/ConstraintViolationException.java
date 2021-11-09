package exceptions;

public class ConstraintViolationException extends Exception {
    public ConstraintViolationException(String message) {
        super(message);
    }

}
