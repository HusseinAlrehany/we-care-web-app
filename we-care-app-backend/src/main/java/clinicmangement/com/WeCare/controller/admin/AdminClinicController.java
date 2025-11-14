package clinicmangement.com.WeCare.controller.admin;

import clinicmangement.com.WeCare.DTO.ClinicDTO;
import clinicmangement.com.WeCare.DTO.ClinicDTOPage;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.admin.AdminClinicService;
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

    @DeleteMapping("/clinic/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClinicById(@PathVariable Integer id){
              adminClinicService.deleteClinicById(id);
        return ResponseEntity.ok(new ApiResponse<>("Deleted Successfully"));
    }

    @GetMapping("/clinic/{id}")
    public ResponseEntity<ClinicDTO> findClinicById(@PathVariable Integer id){

              ClinicDTO clinicDTO =  adminClinicService.findClinicById(id);

        return ResponseEntity.ok(clinicDTO);
    }

    @PutMapping("/clinic-edit/{id}")
    public ResponseEntity<ApiResponse<ClinicDTO>> updateClinic(@RequestBody ClinicDTO clinicDTO, @PathVariable Integer id){
        return ResponseEntity.ok(new ApiResponse<>("Clinic Updated Successfully", adminClinicService.updateClinic(clinicDTO, id)));
    }


}
