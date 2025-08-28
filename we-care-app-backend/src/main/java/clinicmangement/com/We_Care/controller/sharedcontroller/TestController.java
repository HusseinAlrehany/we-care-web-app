package clinicmangement.com.We_Care.controller.sharedcontroller;

import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.repository.visit.VisitBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/Test")
@RequiredArgsConstructor
public class TestController {

    private final VisitBookingRepository visitBookingRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> updateVisitStatus(@RequestParam("today")LocalDate today){

        return ResponseEntity.ok(new ApiResponse<>("updated success affected",
                visitBookingRepository.markPastVisitsAsChecked(today)));
    }


}
