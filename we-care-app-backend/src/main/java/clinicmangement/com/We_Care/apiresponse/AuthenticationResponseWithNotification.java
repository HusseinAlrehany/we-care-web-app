package clinicmangement.com.We_Care.apiresponse;

import clinicmangement.com.We_Care.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponseWithNotification {
    private Integer userId;
    private UserRole userRole;
    private String jwtToken;
    private String notificationToken;
}
