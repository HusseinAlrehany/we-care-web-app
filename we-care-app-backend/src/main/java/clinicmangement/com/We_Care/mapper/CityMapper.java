package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.CityDTO;
import clinicmangement.com.We_Care.models.Cities;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

    public CityDTO cityDTO(Cities city){
        if(city == null){
            throw new NullPointerException("city is null");
        }

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setCityName(city.getCityName());
        cityDTO.setStateId(city.getState().getId());

        return cityDTO;
    }

    public List<CityDTO> toCityDTOList(List<Cities> cities){
        return cities != null ? cities.stream()
                .map(this::cityDTO)
                .toList() : new ArrayList<>();
    }
}
