package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.BookedDoctorDTOProjection;
import clinicmangement.com.WeCare.DTO.VisitBookingDTO;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.service.visitbooking.VisitBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class SharedVisitBookingController {

    private final VisitBookingService visitBookingService;
   // private final JwtUtils jwtUtils;

    @PostMapping("/book-visit")
    public ResponseEntity<ApiResponse<String>> bookVisit(@RequestBody VisitBookingDTO visitBookingDTO,
                                                         @AuthenticationPrincipal User currentUser){

          visitBookingService.bookVisit(visitBookingDTO, currentUser);

        return ResponseEntity.ok(new ApiResponse<>("Visit Booked Successfully"));


    }

    @GetMapping("/booking-info")
    public ResponseEntity<ApiResponse<BookedDoctorDTOProjection>> getBookedDoctor(@RequestParam Integer scheduleId){

        return ResponseEntity.ok(new ApiResponse<>("Success",
                visitBookingService.getBookedDoctorWithSchedule(scheduleId)));
    }



}
