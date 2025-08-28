package clinicmangement.com.We_Care.service.patient;

import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.We_Care.DTO.PatientBookedVisitsProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {

    DoctorClinicScheduleDTOPage getDoctorPage(Pageable pageable);

    DoctorClinicScheduleDTOPage filterDoctorClinicSchedule(Pageable pageable, String doctorName, String specialityName, String stateName, String cityName);


    List<PatientBookedVisitsProjection> getPatientBookedVisits(Integer userId);

    List<PatientBookedVisitsProjection> getPatientCheckedVisits(Integer userId);
}
