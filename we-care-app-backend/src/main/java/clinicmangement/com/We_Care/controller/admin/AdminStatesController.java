package clinicmangement.com.We_Care.controller.admin;

import clinicmangement.com.We_Care.DTO.StateDTO;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.service.admin.AdminStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/we-care")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminStatesController {

    private final AdminStateService adminStateService;

    @PostMapping("/states")
    public ResponseEntity<ApiResponse<StateDTO>> addState(@RequestBody StateDTO stateDTO){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("State Added Success", adminStateService.addState(stateDTO)));
    }
}
