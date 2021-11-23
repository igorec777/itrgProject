package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RowNotFoundException extends Exception {
    public RowNotFoundException(String message) {
        super(message);
    }
}
