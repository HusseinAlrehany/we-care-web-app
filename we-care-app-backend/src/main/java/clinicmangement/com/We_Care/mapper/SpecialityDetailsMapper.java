package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsInfo;
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
public class SpecialityDetailsMapper {

    //not used yet
    public SpecialityDetailsDTOPage toSpecialityDetailsDTOPage(Page<SpecialityDetailsInfo> specialityDetailsInfoPage){
        if(specialityDetailsInfoPage == null){
            throw new NullPointerException("Speciality Details is NULL");
        }

        SpecialityDetailsDTOPage specialityDetailsDTOPage = new SpecialityDetailsDTOPage();

        specialityDetailsDTOPage.setSpecialityDetailsInfoList(specialityDetailsInfoPage.stream().toList());

        specialityDetailsDTOPage.setTotalPages(specialityDetailsInfoPage.getTotalPages());
        specialityDetailsDTOPage.setSize(specialityDetailsInfoPage.getSize());
        specialityDetailsDTOPage.setNumber(specialityDetailsInfoPage.getNumber());
        specialityDetailsDTOPage.setTotalElements(specialityDetailsInfoPage.getTotalElements());
        specialityDetailsDTOPage.setNumberOfElements(specialityDetailsInfoPage.getNumberOfElements());


        return specialityDetailsDTOPage;
    }
}
