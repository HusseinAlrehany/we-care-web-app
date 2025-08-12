package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class DocClinicDTO {
    private Integer id;

    private String clinicMobile;
    private String address;

    private Integer cityId;
    private Integer stateId;

    private String cityName;
    private String stateName;
}
