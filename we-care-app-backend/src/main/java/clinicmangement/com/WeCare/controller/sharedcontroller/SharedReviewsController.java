package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.ReviewsDTOPage;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.shared.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SharedReviewsController {

   // private final PatientService patientService;
   private final SharedService sharedService;

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewsDTOPage>> getReviewsByDoctorId(@RequestParam Integer doctorId,
                                                                            @RequestParam(defaultValue = "0")int pageNumber,
                                                                            @RequestParam(defaultValue = "10")int pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

     return ResponseEntity.ok(new ApiResponse<>("Success",
             sharedService.getReviewsByDoctorId(doctorId,pageable)));
    }

}
