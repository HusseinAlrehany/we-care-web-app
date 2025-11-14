package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.SpecialityDTO;
import clinicmangement.com.WeCare.DTO.SpecialityDetailsDTOPage;

import java.util.List;

public interface AdminSpecialityService {


    SpecialityDTO addSpeciality(SpecialityDTO specialityDTO);

    List<SpecialityDTO> findAll();

    void deleteSpecialityById(Integer id);

    SpecialityDetailsDTOPage getSpecialityDetailsInfo(Integer specialityId, int page, int size);
}
