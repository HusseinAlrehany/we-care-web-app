package clinicmangement.com.WeCare.DTO;

import lombok.Data;

import java.util.List;

@Data
public class SameDoctorsPage {

    List<SameDoctorDTO> content;
    private int totalPages;
    private Long totalElements;
    private int pageSize;
    private int pageNumber;
    private int numberOfElements;


}
