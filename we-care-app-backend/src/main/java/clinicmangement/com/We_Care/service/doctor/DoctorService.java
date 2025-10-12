package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.*;
import clinicmangement.com.We_Care.models.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {

    byte[] getMedicalCardById(Integer id);
    byte[] getDoctorPhotoById(Integer id);

   // SameSpecialityDoctorsPage findAllDoctorsSameSpeciality(String doctorEmail, int pageNumber, int pageSize);

    SameDoctorsPage findAllSameSpecialityDocs(Integer userId, int pageNumber, int pageSize);


    List<ClinicDTOProjection> getAllMyClinicsByUserId(Integer userId);

    void addClinic(Integer userId, DocClinicDTO docClinicDTO);

    UserProfileDTOProjection getUserProfile(Integer userId);

    void updateUserProfile(Integer userId, UserProfileUpdateRequest userProfileUpdateRequest);

    void changePassword(ChangePasswordRequest changePasswordRequest, User user);

    DoctorPreviousVisitsPage getDoctorPreviousVisits(Integer id, Integer pageNumber, Integer pageSize);

    List<DoctorsVisitsDTOProjection> getDoctorTodayVisits(Integer userId, LocalDate today);
}
