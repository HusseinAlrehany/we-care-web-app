package clinicmangement.com.WeCare.DTO;

import clinicmangement.com.WeCare.enums.ScheduleStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DoctorClinicScheduleDTO {
    //doctor
    private Integer doctorId;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String briefIntroduction;
    private LocalDateTime joiningDate;
    private String doctorImageURL;
    private Double totalRating;
    private Double averageRating;
    private Integer specialityId;
    private String specialityName;
    private Integer fees;
    private Long lastUpdated;
    //clinic
    private Integer clinicId;
    private String clinicState;
    private String clinicCity;
    private String address;
    private String clinicMobile;
    //schedule
    private List<ScheduleDTORead> scheduleDTOs;
    private ScheduleStatus scheduleStatus;
}
