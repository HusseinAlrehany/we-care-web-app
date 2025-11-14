package clinicmangement.com.WeCare.controller.Patient;

import clinicmangement.com.WeCare.DTO.PatientBookedVisitsProjection;
import clinicmangement.com.WeCare.DTO.ReviewDTORequest;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
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
