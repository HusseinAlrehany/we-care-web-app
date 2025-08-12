package clinicmangement.com.We_Care.controller.Patient;

import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/we-care/patient")
@PreAuthorize("hasAuthority('PATIENT')")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @GetMapping("/we-care-doctors")
    public ResponseEntity<ApiResponse<DoctorClinicScheduleDTOPage>> getDoctorPage(@RequestParam(defaultValue = "0")int pageNumber,
                                                                                  @RequestParam(defaultValue = "10")int pageSize){
         Pageable pageable = PageRequest.of(pageNumber, pageSize);
      return ResponseEntity.ok(new ApiResponse<>("Success",
              patientService.getDoctorPage(pageable)));
  }


}
