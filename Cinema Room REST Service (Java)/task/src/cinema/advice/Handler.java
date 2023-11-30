package cinema.advice;

import cinema.exception.BookingException;
import cinema.exception.WrongPasswordException;
import cinema.exception.WrongTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), headers, HttpStatusCode.valueOf(401));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), headers, status);
    }

    @ExceptionHandler({BookingException.class, WrongTokenException.class})
    private ResponseEntity<Object> handleBookingAndWrongTokenException(RuntimeException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(WrongPasswordException.class)
    private ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatusCode.valueOf(401));
    }
}