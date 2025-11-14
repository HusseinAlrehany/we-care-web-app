package clinicmangement.com.WeCare.DTO;

import lombok.Data;

import java.util.List;
@Data
public class SpecialityDTO {

    private Integer id;

    private String name;

    private List<DoctorDTO> doctors;

    private Integer numberOfDoctors;
}
