package clinicmangement.com.WeCare.DTO;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DoctorDetailsViewProjection {

    private Integer doctorId;

    private String firstName;
    private String lastName;
    private String specialityName;

    private String briefIntroduction;

    private Integer fees;
    private String doctorImageURL;

    List<ClinicDetailsViewProjection> docClinics = new ArrayList<>();


    public DoctorDetailsViewProjection(Integer doctorId, String firstName,
                                       String lastName, String specialityName,
                                       String briefIntroduction, Integer fees
                             ) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialityName = specialityName;
        this.briefIntroduction = briefIntroduction;
        this.fees = fees;
        this.doctorImageURL = "/doctors/" + doctorId + "/photo";
    }
}
