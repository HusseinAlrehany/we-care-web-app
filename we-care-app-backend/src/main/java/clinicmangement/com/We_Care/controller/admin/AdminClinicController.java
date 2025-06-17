package clinicmangement.com.We_Care.controller.admin;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ClinicDTOPage;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.service.admin.AdminClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/we-care")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminClinicController {

    private final AdminClinicService adminClinicService;


    @PostMapping("/add-clinic")
    public ResponseEntity<ApiResponse<ClinicDTO>> addClinic(@RequestBody ClinicDTO clinicDTO){

        return ResponseEntity.ok(new ApiResponse<>("Success",
                adminClinicService.addClinic(clinicDTO)));
    }

    @GetMapping("/clinics")
    public ResponseEntity<ApiResponse<List<ClinicDTO>>> findAllClinics(){
        return ResponseEntity.ok(new ApiResponse<>("Success", adminClinicService.findAllClinics()));
    }

    @GetMapping("/clinics-page")
    public ResponseEntity<ApiResponse<ClinicDTOPage>> getClinicPage(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5")int size){

        return ResponseEntity.ok(new ApiResponse<>("Success", adminClinicService.getClinicPage(page, size)));

    }


}
