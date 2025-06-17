package clinicmangement.com.We_Care.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

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
