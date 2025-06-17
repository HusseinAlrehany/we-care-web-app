package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.StateDTO;
import clinicmangement.com.We_Care.models.States;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatesMapper {


    public StateDTO toDTO(States states){
        if(states == null){
            throw new NullPointerException("States is NULL");
        }

        StateDTO stateDTO = new StateDTO();
        stateDTO.setId(states.getId());
        stateDTO.setStateName(states.getStateName());


        return stateDTO;
    }

    public List<StateDTO> toStateDTOList(List<States> states){
        return states != null ? states.stream()
                .map(this::toDTO)
                .toList() : new ArrayList<>();
    }

    public States toEntity(StateDTO stateDTO){
        if(stateDTO == null){
            throw new NullPointerException("StateDTO is NULL");
        }

        States state = new States();
        state.setStateName(stateDTO.getStateName());

        return state;
    }

}
