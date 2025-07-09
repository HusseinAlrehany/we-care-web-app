package clinicmangement.com.We_Care.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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


}
