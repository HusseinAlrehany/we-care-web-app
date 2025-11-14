package clinicmangement.com.WeCare.service.patient;

import clinicmangement.com.WeCare.DTO.*;
import clinicmangement.com.WeCare.enums.VisitStatus;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.mapper.DoctorClinicScheduleDTOMapper;
import clinicmangement.com.WeCare.mapper.ReviewDTOMapper;
import clinicmangement.com.WeCare.models.Doctor;
import clinicmangement.com.WeCare.models.Reviews;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.reviews.ReviewsRepository;
import clinicmangement.com.WeCare.repository.visit.VisitBookingRepository;
import clinicmangement.com.WeCare.search.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final DoctorRepository doctorRepository;
    private final DoctorClinicScheduleDTOMapper doctorClinicScheduleDTOMapper;
    private final VisitBookingRepository visitBookingRepository;
    private final ReviewsRepository reviewsRepository;
    private final ReviewDTOMapper reviewDTOMapper;

    //getting doctor with one clinic with all related schedule of that clinic
    //if a doctor has two clinic, this doctor will show twice with each clinic
    @Override
    public DoctorClinicScheduleDTOPage getDoctorPage(Pageable pageable) {
        List <Doctor> doctors = doctorRepository.findAll();

        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found");
        }

        return doctorClinicScheduleDTOMapper.buildDoctorClinicScheduleDTOPage(doctors, pageable);
    }

    @Override
    public DoctorClinicScheduleDTOPage filterDoctorClinicSchedule(Pageable pageable,
                                                                  String doctorName,
                                                                  String specialityName,
                                                                  String stateName,
                                                                  String cityName) {

        Specification<Doctor> doctorSpecification = Specification
                                                  .where(DoctorSpecification.hasName(doctorName))
                .and(DoctorSpecification.hasSpeciality(specialityName))
                .and(DoctorSpecification.hasState(stateName))
                .and(DoctorSpecification.hasCity(cityName));

        List<Doctor> doctors = doctorRepository.findAll(doctorSpecification);

        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found For that Criteria");
        }

        //passing stateName, cityName to call overloaded method to filter stateName, cityName
        return doctorClinicScheduleDTOMapper.buildDoctorClinicScheduleDTOPage(doctors, pageable, stateName, cityName);
    }

    @Override
    public List<PatientBookedVisitsProjection> getPatientBookedVisits(Integer userId) {

        List<PatientBookedVisitsProjection> patientBookedVisitsProjections =
                visitBookingRepository.getPatientBookedVisits(userId, VisitStatus.BOOKED.name());
        if(patientBookedVisitsProjections.isEmpty()){
            throw new NotFoundException("No Booked Visits Found");
        }

        return patientBookedVisitsProjections;
    }

    @Override
    public List<PatientBookedVisitsProjection> getPatientCheckedVisits(Integer userId) {

        //extracting the enum as string using .name()
        List<PatientBookedVisitsProjection> patientCheckedVisitsProjections =
                visitBookingRepository.getPatientBookedVisits(userId, VisitStatus.CHECKED.name());

        if(patientCheckedVisitsProjections.isEmpty()){
            throw new NotFoundException("No Checked Visits Found");
        }
        return patientCheckedVisitsProjections;
    }

    @Override
    public ReviewDTORequest addReview(User user, ReviewDTORequest reviewDTORequest) {

        Doctor doctor = doctorRepository.findById(reviewDTORequest.getDoctorId())
                .orElseThrow(()-> new NotFoundException("No Doctor Found ID: " + reviewDTORequest.getDoctorId()));

        double totalRating = reviewsRepository.getTotalRatingByDoctorId(doctor.getId());
        double averageRating = reviewsRepository.getAverageRatingByDoctorId(doctor.getId());

        Reviews reviews = reviewDTOMapper.toReviews(reviewDTORequest);

        reviews.setDoctor(doctor);
        reviews.setUser(user);
        doctor.setTotalRating(totalRating);
        doctor.setAverageRating(averageRating);

        return reviewDTOMapper.toReviewDTORequest(reviewsRepository.save(reviews));
    }


}
