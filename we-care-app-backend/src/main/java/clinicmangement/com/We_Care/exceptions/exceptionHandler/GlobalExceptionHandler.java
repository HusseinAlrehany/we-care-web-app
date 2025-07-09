package clinicmangement.com.We_Care.exceptions.exceptionHandler;

import clinicmangement.com.We_Care.exceptions.types.InvalidUserNameOrPasswordException;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.exceptions.types.OverLappedSchedulesException;
import clinicmangement.com.We_Care.exceptions.types.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Object> errorResponseBuilder(HttpStatus status,
                                                       String message,
                                                       String messageDetails){

        return new ResponseEntity<>(new ErrorResponse(
              status,
                message,
                LocalDateTime.now(),
                messageDetails),
                status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptionType(Exception ex){
        return errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR,
                                    ex.getMessage(),
                                     ex.getStackTrace().toString());
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return errorResponseBuilder(HttpStatus.CONFLICT,
                                    ex.getMessage(),
                                    ex.getStackTrace().toString());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handeNotFoundException(NotFoundException ex){
        return errorResponseBuilder(HttpStatus.NOT_FOUND,
                                    ex.getMessage(),
                                    ex.getStackTrace().toString());
    }

    @ExceptionHandler(InvalidUserNameOrPasswordException.class)
    public ResponseEntity<Object> handeNotFoundException(InvalidUserNameOrPasswordException ex){
        return errorResponseBuilder(HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ex.getStackTrace().toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentNotValidException ex){
        Map<String, String> errors =new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error-> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OverLappedSchedulesException.class)
    public ResponseEntity<Object> handleOverlappedSchedulesException(OverLappedSchedulesException ex){

        return errorResponseBuilder(HttpStatus.CONFLICT,
                                    ex.getMessage(),
                ex.getStackTrace().toString());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex){
        return errorResponseBuilder(HttpStatus.FORBIDDEN,
                "you are not authorized to access this resource",
                ex.getStackTrace().toString());
    }



}
