package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ClinicDTOProjection;
import clinicmangement.com.We_Care.DTO.SameDoctorsPage;

import java.util.List;

public interface DoctorService {

    byte[] getMedicalCardById(Integer id);
    byte[] getDoctorPhotoById(Integer id);

   // SameSpecialityDoctorsPage findAllDoctorsSameSpeciality(String doctorEmail, int pageNumber, int pageSize);

    SameDoctorsPage findAllSameSpecialityDocs(Integer userId, int pageNumber, int pageSize);


    List<ClinicDTOProjection> getAllMyClinicsByUserId(Integer userId);
}
