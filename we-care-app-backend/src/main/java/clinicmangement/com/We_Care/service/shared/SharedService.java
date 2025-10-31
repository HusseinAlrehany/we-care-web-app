package clinicmangement.com.We_Care.service.shared;


import clinicmangement.com.We_Care.DTO.ReviewsDTOPage;
import org.springframework.data.domain.Pageable;

public interface SharedService {


    ReviewsDTOPage getReviewsByDoctorId(Integer doctorId, Pageable pageable);


}
