package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;
    private String password;
    private String notificationToken;
}
