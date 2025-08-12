package clinicmangement.com.We_Care.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DoctorClinicScheduleDTOPage {

    private List<DoctorClinicScheduleDTO> content;
    private int totalPages;
    private Long totalElements;
    private int size;
    private int number;
    private int numberOfElements;
}
