package clinicmangement.com.WeCare.DTO;

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
