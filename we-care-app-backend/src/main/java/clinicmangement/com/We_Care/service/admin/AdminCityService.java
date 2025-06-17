package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.CityDTO;

import java.util.List;

public interface AdminCityService {

    boolean addCity(CityDTO cityDTO);

    List<CityDTO> findCitiesByStateName(String stateName);

    List<CityDTO> findCitiesByStateId(int stateId);
}
