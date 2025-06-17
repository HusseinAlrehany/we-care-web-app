package clinicmangement.com.We_Care.controller.sharedcontroller;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.service.admin.AdminDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedDoctorController {

private final AdminDoctorService adminDoctorService;

    //uses jpa specification execute query
    @GetMapping("/filter-doctors")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> searchDoctors(@RequestParam(required = false)String doctorName,
                                                                      @RequestParam(required = false)String specialityName,
                                                                      @RequestParam(required = false)String stateName,
                                                                      @RequestParam(required = false)String cityName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminDoctorService.searchDoctors(
                        doctorName,
                        specialityName,
                        stateName,
                        cityName)));

    }
}
