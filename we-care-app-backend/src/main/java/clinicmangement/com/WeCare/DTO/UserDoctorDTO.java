package clinicmangement.com.WeCare.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDoctorDTO {


    private Integer id;
    private String firstName;

    private String lastName;


    private String briefIntroduction;

    private byte[] doctorImage;

    private MultipartFile doctorImgFile;

    private Double totalRating;

    private Double averageRating;

    private Integer userId;

    //@JsonManagedReference
    // private List<DoctorSchedule> doctorScheduleList;

   // private Integer specialityId;
    private String specialityName;
}
