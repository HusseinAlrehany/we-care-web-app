package clinicmangement.com.WeCare.service.shared;


import clinicmangement.com.WeCare.DTO.DoctorDetailsViewProjection;
import clinicmangement.com.WeCare.DTO.ReviewsDTOPage;
import org.springframework.data.domain.Pageable;

public interface SharedService {


    ReviewsDTOPage getReviewsByDoctorId(Integer doctorId, Pageable pageable);

    DoctorDetailsViewProjection getDoctorClinicDetails(Integer doctorId);


}
