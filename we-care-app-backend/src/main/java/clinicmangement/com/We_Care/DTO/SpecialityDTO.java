package clinicmangement.com.We_Care.DTO;

import lombok.Data;

import java.util.List;
@Data
public class SpecialityDTO {

    private Integer id;

    private String name;

    private List<DoctorDTO> doctors;

    private Integer numberOfDoctors;
}
