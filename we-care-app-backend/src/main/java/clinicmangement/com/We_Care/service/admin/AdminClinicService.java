package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ClinicDTOPage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminClinicService {
    ClinicDTO addClinic(ClinicDTO clinicDTO);

    List<ClinicDTO> findAllClinics();

    ClinicDTOPage getClinicPage(int page, int size);
}
