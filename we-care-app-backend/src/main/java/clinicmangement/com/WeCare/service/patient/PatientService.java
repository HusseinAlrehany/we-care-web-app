package clinicmangement.com.WeCare.service.patient;

import clinicmangement.com.WeCare.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.WeCare.DTO.PatientBookedVisitsProjection;
import clinicmangement.com.WeCare.DTO.ReviewDTORequest;
import clinicmangement.com.WeCare.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {

    DoctorClinicScheduleDTOPage getDoctorPage(Pageable pageable);

    DoctorClinicScheduleDTOPage filterDoctorClinicSchedule(Pageable pageable, String doctorName, String specialityName, String stateName, String cityName);

    List<PatientBookedVisitsProjection> getPatientBookedVisits(Integer userId);

    List<PatientBookedVisitsProjection> getPatientCheckedVisits(Integer userId);

    ReviewDTORequest addReview(User user, ReviewDTORequest reviewDTORequest);




}
