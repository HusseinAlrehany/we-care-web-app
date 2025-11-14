package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class ShortDoctorDTO {

    private Integer doctorId;

    private String fullName;

    private String doctorEmail;

    private String specialityName;
}
