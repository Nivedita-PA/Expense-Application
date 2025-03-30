package in.NiveditaPal.expenseTrackerApi.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemAlreadyExitsException extends RuntimeException{
    private static final long serialVersionUID = 1l;
    public ItemAlreadyExitsException(String message){
        super(message);
    }
}
