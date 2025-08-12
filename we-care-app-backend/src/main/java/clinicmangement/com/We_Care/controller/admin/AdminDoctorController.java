package clinicmangement.com.We_Care.controller.admin;

import clinicmangement.com.We_Care.DTO.*;
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


    @GetMapping("/doctor/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(@PathVariable Integer id){

        return ResponseEntity.ok(new ApiResponse<>("Success", adminDoctorService.getDoctorById(id)));
    }

    @PutMapping("/edit-doctor/{id}")
    public ResponseEntity<ApiResponse<DoctorDTODetails>> updateDoctor(@PathVariable Integer id,
                                                                      @ModelAttribute DoctorDTODetails doctorDTODetails){

        return ResponseEntity.ok(new ApiResponse<>("Doctor Updated Success", adminDoctorService.updateDoctor(id, doctorDTODetails)));

    }



}
