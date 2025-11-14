package clinicmangement.com.WeCare.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ScheduleViewProjection {


    Integer getId();
    LocalDate getDate();
    LocalTime getStartTime();
    LocalTime getEndTime();

    String getClinicLocation();
}
