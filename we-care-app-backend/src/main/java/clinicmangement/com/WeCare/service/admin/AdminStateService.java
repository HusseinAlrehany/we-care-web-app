package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.StateDTO;

import java.util.List;

public interface AdminStateService {

    StateDTO addState(StateDTO stateDTO);

    List<StateDTO> findAllStates();
}
