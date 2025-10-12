package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class ReviewDTORequest {

    private Integer id;
    private String comment;
    private Long rating;
    private Integer doctorId;

}
