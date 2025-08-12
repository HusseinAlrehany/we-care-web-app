package clinicmangement.com.We_Care.DTO;

import clinicmangement.com.We_Care.enums.VisitStatus;
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
