package clinicmangement.com.We_Care.exceptions.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime timeStamp;
    private String messageDetails;
}
