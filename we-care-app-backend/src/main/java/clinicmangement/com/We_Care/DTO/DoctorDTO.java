package clinicmangement.com.We_Care.DTO;

import clinicmangement.com.We_Care.models.*;

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


    private List<String> clinicStates;

    private List<String> clinicCities;


   // private List<ClinicDTO> clinicList;

   // private List<VisitBooking> visitBookingList;

    //@JsonManagedReference
    // private List<DoctorSchedule> doctorScheduleList;


    //private byte[] medicalCardImage;
    //private byte[] doctorImage;


}
