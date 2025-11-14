package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.ShortDoctorDTO;
import clinicmangement.com.WeCare.models.Doctor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ShortDoctorDTOMapper {



    public ShortDoctorDTO toShortDoctorDTO(Doctor doctor){
        if(doctor == null){
            throw new NullPointerException("doctor is NULL");
        }

        ShortDoctorDTO shortDoctorDTO = new ShortDoctorDTO();
        shortDoctorDTO.setDoctorId(doctor.getId());
        shortDoctorDTO.setDoctorEmail(doctor.getUser().getEmail());
        shortDoctorDTO.setSpecialityName(doctor.getSpeciality().getName());
        shortDoctorDTO.setFullName(doctor.getFirstName() + " " + doctor.getLastName());

        return shortDoctorDTO;
    }

    public List<ShortDoctorDTO> toShortDoctorDTOList(List<Doctor> doctors){
        return doctors != null ? doctors.stream()
                .map(this::toShortDoctorDTO)
                .sorted(Comparator.comparing(ShortDoctorDTO::getFullName, String.CASE_INSENSITIVE_ORDER))
                .toList() : new ArrayList<>();
    }


}
