package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.DTO.UserDoctorDTO;
import clinicmangement.com.We_Care.models.Doctor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorMapper {


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
        doctorDTO.setUserId(doctor.getUser().getId());
        doctorDTO.setSpecialityId(doctor.getSpeciality().getId());
        doctorDTO.setSpecialityName(doctor.getSpeciality().getName());
       // doctorDTO.setMedicalCardImage(doctor.getMedicalCard());
       // doctorDTO.setDoctorImage(doctor.getDoctorPhoto());
        doctorDTO.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo");
        doctorDTO.setMedicalCardURL("/doctors/" + doctor.getId() + "/medical-card");
        doctorDTO.setTotalRating(doctor.getTotalRating());
        doctorDTO.setAverageRating(doctor.getAverageRating());

        //needed for doctor specification
       List<String> stateNames  = doctor.getClinicList().stream()
                .map(clinic -> clinic.getState().getStateName().name())
                .toList();

        doctorDTO.setClinicStates(stateNames);

        List<String> cityNames = doctor.getClinicList().stream()
                .map(clinic -> clinic.getCity().getCityName())
                .toList();
        doctorDTO.setClinicCities(cityNames);

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



    public byte[] convertImageToByte(MultipartFile img){
        try{
            return img.getBytes();
        }catch(IOException ex){
            throw new RuntimeException("Error processing image");
        }
    }

}
