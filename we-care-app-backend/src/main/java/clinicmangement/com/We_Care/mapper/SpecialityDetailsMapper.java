package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.Speciality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SpecialityDetailsMapper {

    private final ShortDoctorDTOMapper shortDoctorDTOMapper;

    private final ClinicMapper clinicMapper;

    //not used yet
    public SpecialityDetailsDTOPage toSpecialityDetailsDTOPage(Page<Doctor> doctorPage){
        if(doctorPage == null){
            throw new NullPointerException("Doctor Page is NULL");
        }

        List<ShortDoctorDTO> shortDoctorDTOS = doctorPage.getContent()
                .stream()
                .map(shortDoctorDTOMapper::toShortDoctorDTO)
                .toList();


        List<ClinicDTO> clinicDTOS = doctorPage.getContent()
                .stream()
                .flatMap(doctor-> {
                    List<Clinic> clinics = doctor.getClinicList();
                    return clinics != null ? clinics.stream() : Stream.empty();
                })
                .map(clinicMapper::toClinicDTO)
                .toList();


        SpecialityDetailsDTOPage specialityDetailsDTOPage = new SpecialityDetailsDTOPage();
        specialityDetailsDTOPage.setShortDoctorDTOS(shortDoctorDTOS);
        specialityDetailsDTOPage.setClinicDTOS(clinicDTOS);

        specialityDetailsDTOPage.setTotalPages(doctorPage.getTotalPages());
        specialityDetailsDTOPage.setSize(doctorPage.getSize());
        specialityDetailsDTOPage.setNumber(doctorPage.getNumber());
        specialityDetailsDTOPage.setTotalElements(doctorPage.getTotalElements());
        specialityDetailsDTOPage.setNumberOfElements(doctorPage.getNumberOfElements());


        return specialityDetailsDTOPage;
    }
}
