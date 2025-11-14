package clinicmangement.com.WeCare.apiresponse;

import clinicmangement.com.WeCare.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private Integer userId;
    private UserRole userRole;
    private String jwtToken;
}
