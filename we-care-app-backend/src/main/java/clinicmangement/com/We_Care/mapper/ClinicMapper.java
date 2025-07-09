package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ClinicDTOPage;
import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.States;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClinicMapper {



    public ClinicDTO toClinicDTO(Clinic clinic){
        if(clinic == null){
            throw new NullPointerException("Clinic is NULL");
        }

        ClinicDTO clinicDTO = new ClinicDTO();
        clinicDTO.setId(clinic.getId());
        clinicDTO.setClinicLocation(clinic.getClinicLocation());
        clinicDTO.setClinicMobile(clinic.getClinicMobile());
        clinicDTO.setAddress(clinic.getAddress());
        clinicDTO.setDoctorId(clinic.getDoctor().getId());
        clinicDTO.setCityId(clinic.getCity().getId());
        clinicDTO.setStateId(clinic.getState().getId());
        clinicDTO.setDoctorName(clinic.getDoctor().getFirstName() + " " + clinic.getDoctor().getLastName());
        clinicDTO.setCityName(clinic.getCity().getCityName());
        clinicDTO.setStateName(clinic.getState().getStateName().name());


        return clinicDTO;
    }

    public ClinicDTOPage toClinicDTOPage(Page<Clinic> clinicPage){
       if(clinicPage == null){
           throw new NullPointerException("No Clinic Found");
       }

       ClinicDTOPage clinicDTOPage = new ClinicDTOPage();
       clinicDTOPage.setTotalPages(clinicPage.getTotalPages());
       clinicDTOPage.setSize(clinicPage.getSize());
       clinicDTOPage.setNumber(clinicPage.getNumber());
       clinicDTOPage.setTotalElements(clinicPage.getTotalElements());
       clinicDTOPage.setNumberOfElements(clinicPage.getNumberOfElements());

       clinicDTOPage.setContent(clinicPage.getContent().stream().map(this::toClinicDTO).toList());

       return clinicDTOPage;
    }

    public Clinic toClinicEntity(ClinicDTO clinicDTO){
        if(clinicDTO == null){
            throw new NullPointerException("Clinic DTO is NULL");
        }

        Clinic clinic = new Clinic();
        clinic.setAddress(clinicDTO.getAddress());
        clinic.setClinicMobile(clinicDTO.getClinicMobile());
        clinic.setClinicLocation("Not Activated");

        //state, city, doctor will be set in the service

        return clinic;
    }

    public List<ClinicDTO> toClinicDTOList(List<Clinic> clinics){

        return clinics != null ? clinics.stream()
                .map(this::toClinicDTO)
                .toList() : new ArrayList<>();
    }



}
