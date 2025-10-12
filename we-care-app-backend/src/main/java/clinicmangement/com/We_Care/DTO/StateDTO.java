package clinicmangement.com.We_Care.DTO;

import clinicmangement.com.We_Care.enums.StateName;
import lombok.Data;

import java.util.List;

@Data
public class StateDTO {

    private Integer id;

    private StateName stateName;

}
