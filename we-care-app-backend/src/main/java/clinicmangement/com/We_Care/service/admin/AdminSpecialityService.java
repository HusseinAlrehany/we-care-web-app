package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.SpecialityDTO;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;

import java.util.List;

public interface AdminSpecialityService {


    SpecialityDTO addSpeciality(SpecialityDTO specialityDTO);

    List<SpecialityDTO> findAll();

    void deleteSpecialityById(Integer id);

    SpecialityDetailsDTOPage getSpecialityDetailsInfo(Integer specialityId, int page, int size);
}
