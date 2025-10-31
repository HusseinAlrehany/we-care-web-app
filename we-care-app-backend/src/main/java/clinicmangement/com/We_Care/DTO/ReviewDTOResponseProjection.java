package clinicmangement.com.We_Care.DTO;

import java.time.LocalDateTime;

public interface ReviewDTOResponseProjection {

    Integer getReviewId();
    Integer getDoctorId();
    LocalDateTime getCreatedAt();
    Double getTotalRating();
    Double getAverageRating();
    Integer getRating();
    String getComment();
    String getPatientName();


}
