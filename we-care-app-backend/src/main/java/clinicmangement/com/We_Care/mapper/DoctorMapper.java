package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.*;
import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final ScheduleMapper scheduleMapper;

    public DoctorDTO toDTO(Doctor doctor){
        if(doctor == null){
            throw new NullPointerException("Doctor is Null");
        }

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setFirstName(doctor.getFirstName());
        doctorDTO.setLastName(doctor.getLastName());
        doctorDTO.setJoiningDate(doctor.getJoiningDate());
        doctorDTO.setBriefIntroduction(doctor.getBriefIntroduction());
        doctorDTO.setFees(doctor.getFees());
        doctorDTO.setUserId(doctor.getUser().getId());
        doctorDTO.setSpecialityId(doctor.getSpeciality().getId());
        doctorDTO.setSpecialityName(doctor.getSpeciality().getName());
        doctorDTO.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo");
        doctorDTO.setMedicalCardURL("/doctors/" + doctor.getId() + "/medical-card");
        doctorDTO.setTotalRating(doctor.getTotalRating());
        doctorDTO.setAverageRating(doctor.getAverageRating());

        //last updated here is to help recall the new updated img and bypass browser cache
        doctorDTO.setLastUpdated(
                doctor.getLastUpdated()!= null ?
                        doctor.getLastUpdated().toInstant(ZoneOffset.UTC).toEpochMilli()
                        : System.currentTimeMillis()) ;

        //needed for doctor specification
       List<String> stateNames  = doctor.getClinicList().stream()
                .map(clinic -> clinic.getState().getStateName().name())
                .toList();

        doctorDTO.setClinicStates(stateNames);

        List<String> cityNames = doctor.getClinicList().stream()
                .map(clinic -> clinic.getCity().getCityName())
                .toList();
        doctorDTO.setClinicCities(cityNames);

        doctorDTO.setScheduleDTOReads(doctor.getDoctorScheduleList()
                .stream()
                .map(scheduleMapper::toScheduleDTORead)
                .toList());

        return doctorDTO;
    }

    public Doctor toEntity(DoctorDTO doctorDTO){
        if(doctorDTO == null){
            throw new NullPointerException("DoctorDTO is null");
        }

        Doctor doctor = new Doctor();

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setJoiningDate(LocalDateTime.now());
        doctor.setBriefIntroduction(doctor.getBriefIntroduction());

        if(doctorDTO.getMedicalCardFile() != null){
             doctor.setMedicalCard(convertImageToByte(doctorDTO.getMedicalCardFile()));
        }

        if(doctorDTO.getDoctorImgFile() != null){
            doctor.setDoctorPhoto(convertImageToByte(doctorDTO.getDoctorImgFile()));
        }

        return doctor;

    }

    public List<DoctorDTO> toDoctorDTOList(List<Doctor> doctorList){
        return doctorList != null ? doctorList.stream()
                .map(this::toDTO)
                .toList() : new ArrayList<>();

    }

    public UserDoctorDTO userDoctorDTO(Doctor doctor){
            if(doctor == null){
                throw new NullPointerException("Doctor is Null");
            }

        UserDoctorDTO userDoctorDTO = new UserDoctorDTO();
        userDoctorDTO.setId(doctor.getId());
        userDoctorDTO.setFirstName(doctor.getFirstName());
        userDoctorDTO.setLastName(doctor.getLastName());

        userDoctorDTO.setBriefIntroduction(doctor.getBriefIntroduction());
        userDoctorDTO.setUserId(doctor.getUser().getId());
        userDoctorDTO.setSpecialityName(doctor.getSpeciality().getName());
        userDoctorDTO.setDoctorImage(doctor.getDoctorPhoto());
        userDoctorDTO.setTotalRating(doctor.getTotalRating());
        userDoctorDTO.setAverageRating(doctor.getAverageRating());

            return userDoctorDTO;
        }

    public SameDoctorsPage toSameDoctorsPage(Page<Doctor> doctorPage){

        if(doctorPage == null){
            throw new NullPointerException("Doctor Page is NULL");
        }

        SameDoctorsPage sameDoctorsPage = new SameDoctorsPage();
        sameDoctorsPage.setPageNumber(doctorPage.getNumber());
        sameDoctorsPage.setPageSize(doctorPage.getSize());
        sameDoctorsPage.setTotalPages(doctorPage.getTotalPages());
        sameDoctorsPage.setTotalElements(doctorPage.getTotalElements());
        sameDoctorsPage.setNumberOfElements(doctorPage.getNumberOfElements());

        sameDoctorsPage.setContent(doctorPage.getContent().stream().map(this::toSameDoctorDTO).toList());

        return sameDoctorsPage;
    }


    public SameDoctorDTO toSameDoctorDTO(Doctor doctor){
        if(doctor == null){
            throw new NullPointerException("Doctor is NULL");
        }

        SameDoctorDTO sameDoctorDTO = new SameDoctorDTO();
        sameDoctorDTO.setId(doctor.getId());
        sameDoctorDTO.setFees(doctor.getFees());
        sameDoctorDTO.setFullName(doctor.getFirstName() + " " + doctor.getLastName());
        sameDoctorDTO.setBriefIntroduction(doctor.getBriefIntroduction());
        sameDoctorDTO.setAverageRating(doctor.getAverageRating());
        sameDoctorDTO.setTotalRating(doctor.getTotalRating());
        sameDoctorDTO.setSpecialityName(doctor.getSpeciality().getName());
        sameDoctorDTO.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo");

        sameDoctorDTO.setLastUpdated(
                doctor.getLastUpdated() != null ?
                        doctor.getLastUpdated().toInstant(ZoneOffset.UTC).toEpochMilli()
                        : System.currentTimeMillis());

        return sameDoctorDTO;
    }

    public void updateDoctorAndUserProfileFromDTO(Doctor doctor, UserProfileUpdateRequest userProfileUpdateRequest){

        if(doctor == null || userProfileUpdateRequest == null){
            throw new NullPointerException("Doctor or update request is null");
        }

        doctor.setFees(userProfileUpdateRequest.getFees());
        doctor.setBriefIntroduction(userProfileUpdateRequest.getBriefIntroduction());

        if(userProfileUpdateRequest.getDoctorImgFile() != null){
            doctor.setDoctorPhoto(convertImageToByte(userProfileUpdateRequest.getDoctorImgFile()));
        }

        if(userProfileUpdateRequest.getMedicalCardFile() != null){
            doctor.setMedicalCard(convertImageToByte(userProfileUpdateRequest.getMedicalCardFile()));
        }

    }



    public byte[] convertImageToByte(MultipartFile img){
        try{
            return img.getBytes();
        }catch(IOException ex){
            throw new RuntimeException("Error processing image");
        }
    }

}
