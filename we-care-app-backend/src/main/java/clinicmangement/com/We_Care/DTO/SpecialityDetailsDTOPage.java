package clinicmangement.com.We_Care.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SpecialityDetailsDTOPage {

    List<ShortDoctorDTO> shortDoctorDTOS;

    List<ClinicDTO> clinicDTOS;


    private int totalPages;
    private Long totalElements;
    private int size;
    private int number;
    private int numberOfElements;
}
