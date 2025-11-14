package clinicmangement.com.WeCare.DTO;

import lombok.Data;


@Data
public class ClinicDTO {

    private Integer id;
    private String clinicLocation;

    private String clinicMobile;
    private String address;

    private Integer doctorId;
    private Integer cityId;
    private Integer stateId;

    private String doctorName;
    private String cityName;
    private String stateName;
}
