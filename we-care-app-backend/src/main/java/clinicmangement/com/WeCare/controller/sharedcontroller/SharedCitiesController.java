package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.CityDTO;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.admin.AdminCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedCitiesController {

    private final AdminCityService adminCityService;


    @GetMapping("/cities-by-state/{stateName}")
    public ResponseEntity<ApiResponse<List<CityDTO>>> findCitiesByStateName(@PathVariable String stateName){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminCityService.findCitiesByStateName(stateName)));

    }

    @GetMapping("/states/{stateId}/cities")
    public ResponseEntity<ApiResponse<List<CityDTO>>> findCitiesByStateId(@PathVariable int stateId){
        return ResponseEntity.ok(new ApiResponse<>("Success", adminCityService.findCitiesByStateId(stateId)));
    }
}
