package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.StateDTO;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.admin.AdminStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedStatesController {

    private final AdminStateService adminStateService;

    @GetMapping("/states")
    public ResponseEntity<ApiResponse<List<StateDTO>>> getAllStates(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("success", adminStateService.findAllStates()));
    }
}
