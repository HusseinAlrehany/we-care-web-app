package clinicmangement.com.We_Care.controller.Patient;

import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.We_Care.DTO.PatientBookedVisitsProjection;
import clinicmangement.com.We_Care.DTO.ReviewDTORequest;
import clinicmangement.com.We_Care.DTO.ReviewsDTOPage;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/we-care/patient")
@PreAuthorize("hasAuthority('PATIENT')")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @GetMapping("/booked-visits")
  public ResponseEntity<ApiResponse<List<PatientBookedVisitsProjection>>> getPatientBookedVisits(@AuthenticationPrincipal User user){

    return ResponseEntity.ok(new ApiResponse<>("Success", patientService.getPatientBookedVisits(user.getId())));
  }

  @GetMapping("/checked-visits")
  public ResponseEntity<ApiResponse<List<PatientBookedVisitsProjection>>> getPatientCheckedVisitsProjections(@AuthenticationPrincipal User user){

    return ResponseEntity.ok(new ApiResponse<>("Success", patientService.getPatientCheckedVisits(user.getId())));
  }

  @PostMapping("/add-review")
  public ResponseEntity<ApiResponse<?>> addReview(@AuthenticationPrincipal User user,
                                                  @RequestBody ReviewDTORequest reviewDTORequest
                                                  ){


    return ResponseEntity.ok(new ApiResponse<>("Review Added Successfully",
            patientService.addReview(user,reviewDTORequest)));

  }

}
