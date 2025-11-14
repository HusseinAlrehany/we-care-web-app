package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class NotificationTokenRequest {

    private Long id;
    private String notificationToken;
    private Integer doctorId;
}
