package clinicmangement.com.We_Care.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DoctorsVisitsDTOProjection {

    String getPatientName();
    String getPatientMobile();
    LocalDate getVisitDate();
    LocalTime getStartTime();
    LocalTime getEndTime();


}
