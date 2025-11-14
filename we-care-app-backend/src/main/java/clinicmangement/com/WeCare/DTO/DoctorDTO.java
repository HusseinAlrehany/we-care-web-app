package clinicmangement.com.WeCare.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DoctorDTO {


    private Integer id;
    private String firstName;
    private String lastName;

    private String briefIntroduction;
    private LocalDateTime joiningDate;


    private MultipartFile medicalCardFile;
    private String medicalCardURL;

    private MultipartFile doctorImgFile;
    private String doctorImageURL;

    private Double totalRating;

    private Double averageRating;

    private Integer userId;

    private Integer specialityId;

    private String specialityName;

    private Integer fees;

    private Long lastUpdated;

    private List<String> clinicStates;

    private List<String> clinicCities;

    private List<ScheduleDTORead> scheduleDTOReads;




}
