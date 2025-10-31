package clinicmangement.com.We_Care.service.shared;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.DTO.ReviewDTOResponseProjection;
import clinicmangement.com.We_Care.DTO.ReviewsDTOPage;
import clinicmangement.com.We_Care.DTO.SameDoctorsPage;
import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.DoctorMapper;
import clinicmangement.com.We_Care.mapper.ReviewDTOMapper;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.reviews.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SharedServiceImpl implements SharedService{

    private final ReviewsRepository reviewsRepository;
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
}
