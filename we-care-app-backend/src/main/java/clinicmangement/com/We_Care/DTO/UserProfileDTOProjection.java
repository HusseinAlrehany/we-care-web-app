package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class UserProfileDTOProjection {

    private String briefIntroduction;
    private Integer fees;
    private String photoUrl;
    private String medicalCardUrl;

    public UserProfileDTOProjection(String briefIntroduction,
                                    Integer fees, Integer doctorId){

        this.briefIntroduction = briefIntroduction;
        this.fees = fees;
        this.photoUrl = "/doctors/" + doctorId + "/photo";
        this.medicalCardUrl = "/doctors/" + doctorId + "/medical-card";

    }

}
