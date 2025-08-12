package clinicmangement.com.We_Care.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ScheduleDTOWrite {

    //using string type for(date, time) update and create
    //and this strings is parsed to LocalDate, LocalTime before saving to database
    private Integer id;

    private String date;
    private String startTime;
    private String endTime;
    private Integer clinicId;
    private String clinicLocation;
}
