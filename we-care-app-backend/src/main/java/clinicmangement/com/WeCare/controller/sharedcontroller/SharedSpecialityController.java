package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.SpecialityDTO;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.admin.AdminSpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedSpecialityController {

    private final AdminSpecialityService adminSpecialityService;

    @GetMapping("/specialities")
    public ResponseEntity<ApiResponse<List<SpecialityDTO>>> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminSpecialityService.findAll()));
    }
}
