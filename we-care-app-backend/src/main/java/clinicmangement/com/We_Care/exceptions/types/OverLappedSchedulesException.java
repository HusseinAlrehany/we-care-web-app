package clinicmangement.com.We_Care.exceptions.types;

public class OverLappedSchedulesException extends RuntimeException{

    public OverLappedSchedulesException(String message) {
        super(message);
    }

    public OverLappedSchedulesException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverLappedSchedulesException(Throwable cause) {
        super(cause);
    }
}
