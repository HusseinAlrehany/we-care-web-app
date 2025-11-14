package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class ClinicDetailsViewProjection {

    private Integer id;

    private String clinicMobile;
    private String address;
    private String stateName;
    private String cityName;

    public ClinicDetailsViewProjection(Integer id, String clinicMobile,
                                       String address, String stateName,
                                       String cityName) {
        this.id = id;
        this.clinicMobile = clinicMobile;
        this.address = address;
        this.stateName = stateName;
        this.cityName = cityName;
    }
}
