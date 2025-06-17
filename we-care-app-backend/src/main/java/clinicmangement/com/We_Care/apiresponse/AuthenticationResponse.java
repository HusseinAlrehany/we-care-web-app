package clinicmangement.com.We_Care.apiresponse;

import clinicmangement.com.We_Care.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private Integer userId;
    private UserRole userRole;
    private String jwtToken;
}
