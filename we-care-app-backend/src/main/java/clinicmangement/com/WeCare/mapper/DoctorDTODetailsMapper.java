package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.DoctorDTODetails;
import clinicmangement.com.WeCare.models.Doctor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class DoctorDTODetailsMapper {


    public DoctorDTODetails toDoctorDTODetails(Doctor doctor){
        if(doctor == null){
            throw new NullPointerException("Doctor id Null");
        }
        DoctorDTODetails doctorDTODetails = new DoctorDTODetails();
        doctorDTODetails.setId(doctor.getId());
        doctorDTODetails.setSpecialityId(doctor.getSpeciality().getId());
        doctorDTODetails.setFirstName(doctor.getFirstName());
        doctorDTODetails.setLastName(doctor.getLastName());
        doctorDTODetails.setBriefIntroduction(doctor.getBriefIntroduction());
        doctorDTODetails.setSpecialityId(doctor.getSpeciality().getId());
        doctorDTODetails.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo");
        doctorDTODetails.setMedicalCardURL("/doctors/" + doctor.getId() + "/medical-card");


        return doctorDTODetails;
    }

    public Doctor toDoctorEntity(DoctorDTODetails doctorDTODetails){

        if(doctorDTODetails == null){
            throw new NullPointerException("DoctorDTODetails is null");
        }

        Doctor doctor = new Doctor();
        doctor.setId(doctorDTODetails.getId());
        doctor.setBriefIntroduction(doctorDTODetails.getBriefIntroduction());
        doctor.setFirstName(doctorDTODetails.getFirstName());
        doctor.setLastName(doctorDTODetails.getLastName());
        doctor.setJoiningDate(LocalDateTime.now());
        doctor.setFees(doctorDTODetails.getFees());


        if(doctorDTODetails.getDoctorImgFile() != null && !doctorDTODetails.getDoctorImgFile().isEmpty()){
            doctor.setDoctorPhoto(convertImageToBytes(doctorDTODetails.getDoctorImgFile()));
        }
        if(doctorDTODetails.getMedicalCardFile() != null && !doctorDTODetails.getMedicalCardFile().isEmpty()){
            doctor.setMedicalCard(convertImageToBytes(doctorDTODetails.getMedicalCardFile()));
        }

        return doctor;
    }

    //to avoid nulling the not updated fields
    //if we use (toDoctorEntity(DoctorDTODetails doctorDTODetails))
    //it will create new object so any not updated sent fields will be null in the table.
    public void updateDoctorEntityFromDTO(Doctor doctor, DoctorDTODetails doctorDTODetails){

        doctor.setBriefIntroduction(doctorDTODetails.getBriefIntroduction());
        doctor.setFirstName(doctorDTODetails.getFirstName());
        doctor.setLastName(doctorDTODetails.getLastName());
        doctor.setJoiningDate(LocalDateTime.now());
        doctor.setFees(doctorDTODetails.getFees());

        if(doctorDTODetails.getDoctorImgFile() != null && !doctorDTODetails.getDoctorImgFile().isEmpty()){
            doctor.setDoctorPhoto(convertImageToBytes(doctorDTODetails.getDoctorImgFile()));
        }
        if(doctorDTODetails.getMedicalCardFile() != null && !doctorDTODetails.getMedicalCardFile().isEmpty()){
            doctor.setMedicalCard(convertImageToBytes(doctorDTODetails.getMedicalCardFile()));
        }

    }

    public byte[] convertImageToBytes(MultipartFile file){
        try {
            return file.getBytes();
        }catch(IOException ex){
            throw new RuntimeException("Error processing Img");
        }
    }
}
