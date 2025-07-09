package clinicmangement.com.We_Care.controller.admin;

import clinicmangement.com.We_Care.DTO.SpecialityDTO;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.service.admin.AdminSpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/we-care")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminSpecialityController {


    private final AdminSpecialityService adminSpecialityService;
    @PostMapping("/add-speciality")
    public ResponseEntity<ApiResponse<SpecialityDTO>> addSpeciality(@RequestBody SpecialityDTO specialityDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Speciality Added Successfully", adminSpecialityService.addSpeciality(specialityDTO)));
    }

    @GetMapping("/specialities")
    public ResponseEntity<ApiResponse<List<SpecialityDTO>>> findAllSpecialities(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminSpecialityService.findAll()));
    }

    @DeleteMapping("/speciality/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSpecialityById(@PathVariable Integer id){

         adminSpecialityService.deleteSpecialityById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Deleted Success", null));
    }

    @GetMapping("/speciality-details-info/{specialityId}")
    public ResponseEntity<ApiResponse<SpecialityDetailsDTOPage>> getSpecialityDetailsInfo(@PathVariable Integer specialityId,
                                                                                             @RequestParam(defaultValue = "0")int page,
                                                                                             @RequestParam(defaultValue = "4")int size){

        return ResponseEntity.ok(new ApiResponse<>("Success",
                adminSpecialityService.getSpecialityDetailsInfo(specialityId, page, size)));
    }


}
