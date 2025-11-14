package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.StateDTO;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.mapper.StatesMapper;
import clinicmangement.com.WeCare.models.States;
import clinicmangement.com.WeCare.repository.states.StateRepository;
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
