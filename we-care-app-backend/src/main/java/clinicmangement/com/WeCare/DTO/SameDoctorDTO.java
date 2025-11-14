package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class SameDoctorDTO {

    private Integer id;

    private String fullName;

    private String briefIntroduction;

    private String doctorImageURL;

    private Double totalRating;

    private Double averageRating;

    private String specialityName;

    private Integer fees;

    private Long lastUpdated;


}
