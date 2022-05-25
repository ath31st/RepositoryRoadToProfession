package account.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;

@ControllerAdvice
public class CustomResponseException {
   // @ExceptionHandler
    public ResponseEntity<Response> handle400Exception(ResponseStatusException e) {
        Response response = new Response();
        response.setTimestamp(LocalDate.now());
        response.setStatus(e.getStatus().value());
        response.setError(e.getStatus().getReasonPhrase());
        response.setMessage(e.getReason());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

