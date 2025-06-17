package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.StateDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.models.States;

import java.util.List;

public interface AdminStateService {

    StateDTO addState(StateDTO stateDTO);

    List<StateDTO> findAllStates();
}
