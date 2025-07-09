package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class ScheduleDTOSTR {

    private Integer id;
    //using string type for update
    private String scDate;
    private String scStartTime;
    private String scEndTime;
}
