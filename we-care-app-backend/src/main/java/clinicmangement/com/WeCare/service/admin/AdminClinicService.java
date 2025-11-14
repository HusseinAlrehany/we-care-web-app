package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.ClinicDTO;
import clinicmangement.com.WeCare.DTO.ClinicDTOPage;

import java.util.List;

public interface AdminClinicService {
    ClinicDTO addClinic(ClinicDTO clinicDTO);

    List<ClinicDTO> findAllClinics();

    ClinicDTOPage getClinicPage(int page, int size);

    void deleteClinicById(Integer clinicId);


    ClinicDTO findClinicById(Integer id);

    ClinicDTO updateClinic(ClinicDTO clinicDTO, Integer id);
}
