package account.exceptionhandler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password length must be 12 chars minimum!")
public class InvalidLengthPasswordException extends RuntimeException{
}
