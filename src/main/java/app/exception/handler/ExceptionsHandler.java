package app.exception.handler;

import app.exception.ReferenceRestrictionException;
import app.exception.RowNotFoundException;
import app.exception.UnableToUpdateException;
import app.exception.UniqueRestrictionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RowNotFoundException.class)
    protected ResponseEntity<Object> handleRowNotFoundException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ReferenceRestrictionException.class)
    protected ResponseEntity<Object> handleReferenceRestrictionException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    //which http status is more suitable here?
    @ExceptionHandler(UniqueRestrictionException.class)
    protected ResponseEntity<Object> handleUniqueRestrictionException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //which http status is more suitable here?
    @ExceptionHandler(UnableToUpdateException.class)
    protected ResponseEntity<Object> handleUnableToUpdateException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String responseBody = "Method: " + ex.getMethod() + " not allowed for this URI";
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    //bad request body
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.setContentType(MediaType.TEXT_PLAIN);
        return handleExceptionInternal(ex, methodArgumentNotValidMessage(ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    private String methodArgumentNotValidMessage(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder("Request data is invalid:\n");
        List<FieldError> errorFields = ex.getFieldErrors();
        for (FieldError errorField : errorFields) {
            sb.append("'")
                    .append(errorField.getField())
                    .append("' ")
                    .append(errorField.getDefaultMessage())
                    .append("\n");
        }
        return sb.toString();
    }
}
