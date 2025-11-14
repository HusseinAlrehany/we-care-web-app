package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class BookedDoctorDTO {

    private Integer id;
    private String fullName;
    private String doctorPhotoURL;
    private Integer specialityId;
    private String specialityName;
    private Integer scheduleId;
}
