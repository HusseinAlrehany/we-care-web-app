package clinicmangement.com.We_Care.apiresponse;

public record ApiResponse<T>(String message, T payload){
    public ApiResponse(String message){
        this(message,null);
    }

}





