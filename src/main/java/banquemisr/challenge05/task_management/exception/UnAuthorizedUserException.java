package banquemisr.challenge05.task_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnAuthorizedUserException extends RuntimeException{
    public UnAuthorizedUserException(String message){
        super(message);
    }
}
