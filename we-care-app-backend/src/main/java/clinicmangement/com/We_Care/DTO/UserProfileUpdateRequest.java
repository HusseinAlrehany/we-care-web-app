package clinicmangement.com.We_Care.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfileUpdateRequest {

    private String briefIntroduction;
    private Integer fees;
    private MultipartFile medicalCardFile;
    private MultipartFile doctorImgFile;
}
