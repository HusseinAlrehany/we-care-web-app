package clinicmangement.com.We_Care.service.patient;

import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTOPage;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    DoctorClinicScheduleDTOPage getDoctorPage(Pageable pageable);

    DoctorClinicScheduleDTOPage filterDoctorClinicSchedule(Pageable pageable, String doctorName, String specialityName, String stateName, String cityName);
}
