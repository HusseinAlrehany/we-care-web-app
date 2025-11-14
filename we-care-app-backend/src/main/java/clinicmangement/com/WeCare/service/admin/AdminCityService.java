package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.CityDTO;

import java.util.List;

public interface AdminCityService {

    boolean addCity(CityDTO cityDTO);

    List<CityDTO> findCitiesByStateName(String stateName);

    List<CityDTO> findCitiesByStateId(int stateId);
}
