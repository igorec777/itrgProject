package exceptions;

public class RowNotFoundException extends Exception {

    public RowNotFoundException(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " with id:" + id + " not found");
    }
}
