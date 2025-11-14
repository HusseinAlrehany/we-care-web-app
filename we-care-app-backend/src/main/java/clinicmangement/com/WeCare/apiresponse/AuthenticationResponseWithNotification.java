package clinicmangement.com.WeCare.apiresponse;

import clinicmangement.com.WeCare.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponseWithNotification {
    private Integer userId;
    private UserRole userRole;
    private String jwtToken;
    private String notificationToken;
}
