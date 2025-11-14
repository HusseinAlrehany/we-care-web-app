package clinicmangement.com.WeCare.DTO;

import clinicmangement.com.WeCare.enums.StateName;
import lombok.Data;

@Data
public class StateDTO {

    private Integer id;

    private StateName stateName;

}
