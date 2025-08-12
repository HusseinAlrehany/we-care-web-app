package clinicmangement.com.We_Care.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookedDoctorDTOProjection {

    Integer getDoctorId();
    String getFullName();
    Integer getSpecialityId();
    String getSpecialityName();
    Integer getScheduleId();
    LocalDate getDate();
    LocalTime getStartTime();
    LocalTime getEndTime();
}
