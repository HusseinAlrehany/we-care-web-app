package clinicmangement.com.WeCare.controller.doctor;

import clinicmangement.com.WeCare.DTO.*;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.service.doctor.DoctorService;
import clinicmangement.com.WeCare.service.doctor.ScheduleService;
import clinicmangement.com.WeCare.token.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/we-care")
@PreAuthorize("hasAuthority('DOCTOR')")
@RequiredArgsConstructor
public class DoctorController {

    private final ScheduleService scheduleService;

    private final DoctorService doctorService;

    private final JwtUtils jwtUtils;
    //there is also Principle interface(basic user info (userName)), @AuthenticationPrinciple (the entire user details)
    //instead of sending the doctor id with the request body
    //using principle to extract the email from http only cookie(session storage)
    @PostMapping("/doctor/add-schedule")
    public ResponseEntity<ApiResponse<String>> addDailySchedule(@RequestBody ScheduleDTOWrite scheduleDTOWrite){

            User user = jwtUtils.getLoggedInUser();
             Integer userId = user.getId();
             scheduleService.createDailySchedule(scheduleDTOWrite, userId);
        return ResponseEntity.ok(new ApiResponse<>("Schedule Added Successfully"));
    }

    @GetMapping("/doctor/my-speciality")
    public ResponseEntity<ApiResponse<SameDoctorsPage>> getSameSpecialityDoctors(
                                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                                 @RequestParam(defaultValue = "2") int pageSize){
        User user = jwtUtils.getLoggedInUser();
        Integer userId = user.getId();

        return ResponseEntity.ok(new ApiResponse<>("Success", doctorService.findAllSameSpecialityDocs(
                userId, pageNumber, pageSize)));


    }

    @GetMapping("/doctor/my-clinics")
    public ResponseEntity<ApiResponse<List<ClinicDTOProjection>>> getAllMyClinics(){
            User user = jwtUtils.getLoggedInUser();
            Integer userId = user.getId();

            return ResponseEntity.ok(new ApiResponse<>("Success",
                    doctorService.getAllMyClinicsByUserId(userId)));
    }

    @GetMapping("/doctor/schedules")
    public ResponseEntity<ApiResponse<Page<ScheduleDTOProjection>>> getAllMySchedules(
                                                                                      @RequestParam(defaultValue = "0")int pageNumber,
                                                                                      @RequestParam(defaultValue = "5")int pageSize
                                                                                      ){

        User user = jwtUtils.getLoggedInUser();
        Integer userId = user.getId();

        return ResponseEntity.ok(new ApiResponse<>("Success",
                scheduleService.findAllSchedulesByUserId(userId, pageNumber, pageSize)));
    }

    @DeleteMapping("/doctor/schedule/{scheduleId}")
    public ResponseEntity<ApiResponse<Void>> deleteScheduleById(@PathVariable Integer scheduleId){
         scheduleService.deleteScheduleById(scheduleId);
        return ResponseEntity.ok(new ApiResponse<>("Schedule deleted Successfully",
                null));
    }

    @GetMapping("/doctor/schedule/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleDTORead>> getScheduleById(@PathVariable Integer scheduleId){

        return ResponseEntity.ok(new ApiResponse<>("Success",
                scheduleService.getScheduleById(scheduleId)));
    }


    @PutMapping("/doctor/edit-schedule/{scheduleId}")
    public ResponseEntity<ApiResponse<String>> updateSchedule(@PathVariable Integer scheduleId,
                                                              @RequestBody ScheduleDTOWrite scheduleDTOWrite)
    {

            scheduleService.updateSchedule(scheduleId, scheduleDTOWrite);

        return ResponseEntity.ok(new ApiResponse<>("Schedule Updated Success"));

    }

    @PostMapping("/doctor/add-clinic")
    public ResponseEntity<ApiResponse<String>> addMyClinic(@RequestBody DocClinicDTO docClinicDTO)
    {
           User user = jwtUtils.getLoggedInUser();
           Integer userId = user.getId();
           doctorService.addClinic(userId, docClinicDTO);

        return ResponseEntity.ok(new ApiResponse<>("Clinic Added Successfully"));

    }

    @GetMapping("/doctor/profile")
    public ResponseEntity<ApiResponse<UserProfileDTOProjection>> getUserProfile(){
        User user = jwtUtils.getLoggedInUser();
        Integer userId = user.getId();

        return ResponseEntity.ok(new ApiResponse<>("Success", doctorService.getUserProfile(userId)));
    }

    @PutMapping("/doctor/update-profile")
    public ResponseEntity<ApiResponse<String>> updateUserProfile(@ModelAttribute UserProfileUpdateRequest userProfileUpdateRequest){
        User user = jwtUtils.getLoggedInUser();
        Integer userId = user.getId();

        doctorService.updateUserProfile(userId, userProfileUpdateRequest);

        return ResponseEntity.ok(new ApiResponse<>("Profile Updated Successfully"));

    }

    @PutMapping("/doctor/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        User user = jwtUtils.getLoggedInUser();

        doctorService.changePassword(changePasswordRequest, user);

        return ResponseEntity.ok(new ApiResponse<>("Password Changed Successfully, please signin again"));
    }

    @GetMapping("/doctor/visits-history")
    public ResponseEntity<ApiResponse<DoctorPreviousVisitsPage>> getDoctorsPreviousVisits(@AuthenticationPrincipal User user,
                                                                                          @RequestParam(defaultValue = "0")Integer pageNumber,
                                                                                          @RequestParam(defaultValue = "10")Integer pageSize
                                                                                                         ){



        return ResponseEntity.ok(new ApiResponse<>("Success" ,doctorService.getDoctorPreviousVisits(user.getId(), pageNumber, pageSize)));

    }

    @GetMapping("/doctor/today-visits")
    public ResponseEntity<ApiResponse<List<DoctorsVisitsDTOProjection>>> getDoctorsTodayVisits(@AuthenticationPrincipal User user,
                                                                                               @RequestParam(required = false)LocalDate today){
        return ResponseEntity.ok(new ApiResponse<>("Success",
                doctorService.getDoctorTodayVisits(user.getId(), today)));
    }


}
