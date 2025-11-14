package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.DoctorsVisitsDTOProjection;
import clinicmangement.com.WeCare.DTO.DoctorPreviousVisitsPage;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class DoctorPreviousVisitsMapper {



    public DoctorPreviousVisitsPage toDoctorPreviousVisitsPage(Page<DoctorsVisitsDTOProjection> doctorPreviousVisitsDTOProjection){

        if(doctorPreviousVisitsDTOProjection == null){
            throw new NullPointerException("Projection is null");
        }

        DoctorPreviousVisitsPage doctorPreviousVisitsPage = new DoctorPreviousVisitsPage();
        doctorPreviousVisitsPage.setContent(doctorPreviousVisitsDTOProjection.stream().toList());
        doctorPreviousVisitsPage.setTotalPages(doctorPreviousVisitsDTOProjection.getTotalPages());
        doctorPreviousVisitsPage.setSize(doctorPreviousVisitsDTOProjection.getSize());
        doctorPreviousVisitsPage.setNumber(doctorPreviousVisitsDTOProjection.getNumber());
        doctorPreviousVisitsPage.setNumberOfElements(doctorPreviousVisitsDTOProjection.getNumberOfElements());
        doctorPreviousVisitsPage.setTotalElements(doctorPreviousVisitsDTOProjection.getTotalElements());


        return doctorPreviousVisitsPage;


    }
}
