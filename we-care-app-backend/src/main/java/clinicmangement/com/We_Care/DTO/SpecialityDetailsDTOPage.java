package clinicmangement.com.We_Care.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SpecialityDetailsDTOPage {


    List<SpecialityDetailsInfoProjection> specialityDetailsInfoProjectionList;

    private int totalPages;
    private Long totalElements;
    private int size;
    private int number;
    private int numberOfElements;
}
