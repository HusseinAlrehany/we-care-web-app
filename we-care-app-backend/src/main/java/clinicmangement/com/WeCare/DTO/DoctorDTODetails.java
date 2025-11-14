package clinicmangement.com.WeCare.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DoctorDTODetails {

    private Integer id;

    private String firstName;
    private String lastName;

    private String briefIntroduction;

    private Integer fees;

    private MultipartFile medicalCardFile;
    private String medicalCardURL;

    private MultipartFile doctorImgFile;
    private String doctorImageURL;

    private Integer specialityId;

}
