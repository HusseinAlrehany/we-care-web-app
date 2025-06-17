package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.CityDTO;
import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.CityMapper;
import clinicmangement.com.We_Care.models.Cities;
import clinicmangement.com.We_Care.repository.city.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCityServiceImpl implements AdminCityService{

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    @Override
    public boolean addCity(CityDTO cityDTO) {
        return false;
    }

    @Override
    public List<CityDTO> findCitiesByStateName(String stateName) {

        StateName stateName1 = StateName.fromStringToStateEnum(stateName);

        List<Cities> cities = cityRepository.findAllByState_StateName(stateName1);
        if(cities.isEmpty()){
            throw new NotFoundException("No Cities for that state: " + stateName);
        }
        return cityMapper.toCityDTOList(cities);
    }

    @Override
    public List<CityDTO> findCitiesByStateId(int stateId) {
         List<Cities> cities = cityRepository.findAllByState_Id(stateId);

         if(cities.isEmpty()){
             throw new NotFoundException("No Cities Found for this State!!");
         }

        return cityMapper.toCityDTOList(cities);
    }
}
