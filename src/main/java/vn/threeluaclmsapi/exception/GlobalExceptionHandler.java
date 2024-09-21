package vn.threeluaclmsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.threeluaclmsapi.dto.response.ResponseError;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class, RuntimeException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleValidationException(Exception ex, WebRequest request) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            int start = ex.getMessage().lastIndexOf("[");
            int end = ex.getMessage().lastIndexOf("]");
            message = message.substring(start + 1, end - 1);
            error.setMessage(message);
        } else if (ex instanceof RuntimeException) {
            error.setMessage(message);
        }

        return error;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleResourceAlreadyExists(ResourceAlreadyExistsException ex, WebRequest request) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setError(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return error;
    }

}
