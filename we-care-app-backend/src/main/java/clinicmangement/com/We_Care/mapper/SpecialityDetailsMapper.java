package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class SpecialityDetailsMapper {

    //not used yet
    public SpecialityDetailsDTOPage toSpecialityDetailsDTOPage(Page<SpecialityDetailsInfoProjection> specialityDetailsInfoPage){
        if(specialityDetailsInfoPage == null){
            throw new NullPointerException("Speciality Details is NULL");
        }

        SpecialityDetailsDTOPage specialityDetailsDTOPage = new SpecialityDetailsDTOPage();

        specialityDetailsDTOPage.setSpecialityDetailsInfoProjectionList(specialityDetailsInfoPage.stream().toList());

        specialityDetailsDTOPage.setTotalPages(specialityDetailsInfoPage.getTotalPages());
        specialityDetailsDTOPage.setSize(specialityDetailsInfoPage.getSize());
        specialityDetailsDTOPage.setNumber(specialityDetailsInfoPage.getNumber());
        specialityDetailsDTOPage.setTotalElements(specialityDetailsInfoPage.getTotalElements());
        specialityDetailsDTOPage.setNumberOfElements(specialityDetailsInfoPage.getNumberOfElements());


        return specialityDetailsDTOPage;
    }
}
