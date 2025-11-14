package clinicmangement.com.WeCare.controller.sharedcontroller;

import clinicmangement.com.WeCare.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.WeCare.DTO.DoctorDTO;
import clinicmangement.com.WeCare.DTO.DoctorDetailsViewProjection;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.service.admin.AdminDoctorService;
import clinicmangement.com.WeCare.service.patient.PatientService;
import clinicmangement.com.WeCare.service.shared.SharedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SharedDoctorController {

    //TO DO -> pulling the old code from github
    //because the recent filter not work
private final AdminDoctorService adminDoctorService;

private final PatientService patientService;

private final SharedService sharedService;


    //uses jpa specification execute query
    //used in admin dashboard
    @GetMapping("/filter-doctors")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> searchDoctors(@RequestParam(required = false)String doctorName,
                                                                      @RequestParam(required = false)String specialityName,
                                                                      @RequestParam(required = false)String stateName,
                                                                      @RequestParam(required = false)String cityName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Success", adminDoctorService.searchDoctors(
                        doctorName,
                        specialityName,
                        stateName,
                        cityName)));

    }

    //uses Jpa specification query
    //used in patient dashboard
   @GetMapping("/filter-doctor-clinic-schedule")
   public ResponseEntity<ApiResponse<DoctorClinicScheduleDTOPage>> filterDoctorClinicSchedule(@RequestParam(defaultValue = "0")int pageNumber,
                                                                                              @RequestParam(defaultValue = "10")int pageSize,
                                                                                              @RequestParam(required = false)String doctorName,
                                                                                              @RequestParam(required = false)String specialityName,
                                                                                              @RequestParam(required = false)String stateName,
                                                                                              @RequestParam(required = false)String cityName){
       Pageable pageable = PageRequest.of(pageNumber, pageSize);

       return ResponseEntity.status(HttpStatus.OK)
               .body(new ApiResponse<>("Success", patientService.filterDoctorClinicSchedule(
                       pageable,
                       doctorName,
                       specialityName,
                       stateName,
                       cityName
               )));

   }
   
    @GetMapping("/we-care-doctors")
    public ResponseEntity<ApiResponse<DoctorClinicScheduleDTOPage>> getDoctorPage(@RequestParam(defaultValue = "0")int pageNumber,
                                                                                  @RequestParam(defaultValue = "10")int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(new ApiResponse<>("Success",
                patientService.getDoctorPage(pageable)));
    }


    @GetMapping("/doctor-details")
    public ResponseEntity<ApiResponse<DoctorDetailsViewProjection>> getDoctorDetailsView(@RequestParam Integer doctorId){

        return ResponseEntity.ok(new ApiResponse<>("Success",sharedService.getDoctorClinicDetails(doctorId)));
    }
}
