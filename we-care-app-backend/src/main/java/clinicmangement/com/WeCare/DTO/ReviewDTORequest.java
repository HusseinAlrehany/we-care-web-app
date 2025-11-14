package clinicmangement.com.WeCare.DTO;

import lombok.Data;

@Data
public class ReviewDTORequest {

    private Integer id;
    private String comment;
    private Long rating;
    private Double totalRating;
    private Double averageRating;
    private Integer doctorId;

}
