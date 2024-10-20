package banquemisr.challenge05.task_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED)
public class NotFoundTasksException extends RuntimeException{
    public NotFoundTasksException(String message){
        super(message);
    }
}
