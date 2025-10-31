package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class NotificationTokenRequest {

    private Long id;
    private String notificationToken;
    private Integer doctorId;
}
