package clinicmangement.com.We_Care.controller.admin;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.DTO.DoctorSignupRequest;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.DTO.UserDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.service.admin.AdminDoctorService;
import clinicmangement.com.We_Care.token.jwtservice.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/we-care")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDoctorController {

    private final AdminDoctorService adminDoctorService;

    private final AuthenticationService authenticationService;

    @GetMapping("/doctors")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getAllDoctors(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminDoctorService.findAllDoctors()));
    }

    @GetMapping("/all-short-doctor")
    public ResponseEntity<ApiResponse<List<ShortDoctorDTO>>> getShortDoctors(){
        return ResponseEntity.ok(new ApiResponse<>("Success", adminDoctorService.getAllShortDoctors()));
    }

    @DeleteMapping("/doctor/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctorById(@PathVariable Integer id){
        adminDoctorService.deleteDoctor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Doctor Deleted Success", null));
    }

    @PostMapping("/add-doctor")
    public ResponseEntity<ApiResponse<UserDTO>> addDoctor(@ModelAttribute @Valid DoctorSignupRequest doctorSignupRequest) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Doctor Added Successfully", authenticationService.signUpAsDoctor(doctorSignupRequest)));
    }


    //uses JPQL query with join fetch.
    @GetMapping("/search-doctors")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> filterDoctors(@RequestParam(required = false) String doctorName,
                                                                      @RequestParam(required = false) String specialityName,
                                                                      @RequestParam(required = false) String stateName,
                                                                      @RequestParam(required = false) String cityName){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminDoctorService.filterDoctors(
                       doctorName, specialityName, stateName, cityName)));



    }
}
