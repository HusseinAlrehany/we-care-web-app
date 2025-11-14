package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class PatientSignupRequest {

    //doctor and patient shared fields
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;

}
