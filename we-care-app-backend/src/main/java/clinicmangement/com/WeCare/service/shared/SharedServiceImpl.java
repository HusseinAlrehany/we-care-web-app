package clinicmangement.com.WeCare.service.shared;

import clinicmangement.com.WeCare.DTO.ClinicDetailsViewProjection;
import clinicmangement.com.WeCare.DTO.DoctorDetailsViewProjection;
import clinicmangement.com.WeCare.DTO.ReviewDTOResponseProjection;
import clinicmangement.com.WeCare.DTO.ReviewsDTOPage;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.mapper.ReviewDTOMapper;
import clinicmangement.com.WeCare.repository.clinic.ClinicRepository;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.reviews.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SharedServiceImpl implements SharedService{

    private final ReviewsRepository reviewsRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final ReviewDTOMapper reviewDTOMapper;

    @Override
    public ReviewsDTOPage getReviewsByDoctorId(Integer doctorId, Pageable pageable) {

        Page<ReviewDTOResponseProjection> reviewDTOResponseProjectionPage =
                reviewsRepository.getReviewsByDoctorId(doctorId, pageable);
        if(!reviewDTOResponseProjectionPage.hasContent()){
            throw new NotFoundException("No Reviews Found For That Doctor!");
        }

        return reviewDTOMapper.toReviewDTOPage(reviewDTOResponseProjectionPage);
    }

    @Transactional
    @Override
    public DoctorDetailsViewProjection getDoctorClinicDetails(Integer doctorId) {
        DoctorDetailsViewProjection doctorDetailsViewProjection =
                doctorRepository.getDoctorDetails(doctorId);

        List<ClinicDetailsViewProjection> clinicDetailsViewProjection =
                clinicRepository.getClinicDetailsView(doctorId);

        if(doctorDetailsViewProjection == null || clinicDetailsViewProjection == null){
            throw new NotFoundException("No Doctor Details or Clinics Found For id: ");
        }

        doctorDetailsViewProjection.setDocClinics(clinicDetailsViewProjection);

        return doctorDetailsViewProjection;
    }
}
