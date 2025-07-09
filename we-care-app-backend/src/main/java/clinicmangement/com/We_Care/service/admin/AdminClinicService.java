package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ClinicDTOPage;

import java.util.List;

public interface AdminClinicService {
    ClinicDTO addClinic(ClinicDTO clinicDTO);

    List<ClinicDTO> findAllClinics();

    ClinicDTOPage getClinicPage(int page, int size);

    void deleteClinicById(Integer clinicId);


    ClinicDTO findClinicById(Integer id);

    ClinicDTO updateClinic(ClinicDTO clinicDTO, Integer id);
}
