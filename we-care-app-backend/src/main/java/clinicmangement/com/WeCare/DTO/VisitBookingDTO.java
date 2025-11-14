package clinicmangement.com.WeCare.DTO;

import clinicmangement.com.WeCare.enums.VisitStatus;
import lombok.Data;

@Data
public class VisitBookingDTO {

    private Integer id;
    private String patientName;
    private String patientMobile;
    private Integer doctorId;
    private Integer clinicId;
    private Integer scheduleId;
    private Integer userId;
    private VisitStatus visitStatus;

}
