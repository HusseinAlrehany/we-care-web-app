package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.StateDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.StatesMapper;
import clinicmangement.com.We_Care.models.States;
import clinicmangement.com.We_Care.repository.states.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminStateServiceImpl implements AdminStateService{

  private final StateRepository stateRepository;

  private final StatesMapper statesMapper;
    @Override
    public StateDTO addState(StateDTO stateDTO) {
        States states = statesMapper.toEntity(stateDTO);
        stateRepository.save(states);

        return statesMapper.toDTO(states);
    }

    @Override
    public List<StateDTO> findAllStates() {
        List<States> states = stateRepository.findAll();
        if(states.isEmpty()){
            throw new NotFoundException("No states Found");
        }
        return statesMapper.toStateDTOList(states);
    }
}
