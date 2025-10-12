package clinicmangement.com.We_Care.DTO;

import lombok.Data;

import java.util.List;

@Data
public class DoctorPreviousVisitsPage {

    private List<DoctorsVisitsDTOProjection> content;
    private int totalPages;
    private Long totalElements;
    private int size;
    private int number;
    private int numberOfElements;
}
