package clinicmangement.com.We_Care.controller.doctor;

import clinicmangement.com.We_Care.DTO.*;
import clinicmangement.com.We_Care.apiresponse.ApiResponse;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.service.doctor.DoctorService;
import clinicmangement.com.We_Care.service.doctor.ScheduleService;
import clinicmangement.com.We_Care.token.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/we-care")
@PreAuthorize("hasAuthority('DOCTOR')")
@RequiredArgsConstructor
public class DoctorController {

    private final ScheduleService scheduleService;

    private final DoctorService doctorService;

    private final JwtUtils jwtUtils;

    //instead of sending the doctor id with the request body
    //using principle to extract the email from http only cookie(session storage)
    @PostMapping("/doctor/add-schedule")
    public ResponseEntity<ApiResponse<String>> addDailySchedule(@RequestBody ScheduleDTO scheduleDTO, Integer userId){

            User user = jwtUtils.getLoggedInUser();
             userId = user.getId();
             scheduleService.createDailySchedule(scheduleDTO, userId);
        return ResponseEntity.ok(new ApiResponse<>("Schedule Added Successfully"));
    }

    @GetMapping("/doctor/my-speciality")
    public ResponseEntity<ApiResponse<SameDoctorsPage>> getSameSpecialityDoctors(Integer userId,
                                                                                 @RequestParam(defaultValue = "0") int pageNumber,
                                                                                 @RequestParam(defaultValue = "2") int pageSize){
        User user = jwtUtils.getLoggedInUser();
        userId = user.getId();

        return ResponseEntity.ok(new ApiResponse<>("Success", doctorService.findAllSameSpecialityDocs(
                userId, pageNumber, pageSize)));


    }

    @GetMapping("/doctor/my-clinics")
    public ResponseEntity<ApiResponse<List<ClinicDTOProjection>>> getAllMyClinics(Integer userId){
            User user = jwtUtils.getLoggedInUser();
            userId = user.getId();

            return ResponseEntity.ok(new ApiResponse<>("Success",
                    doctorService.getAllMyClinicsByUserId(userId)));
    }

    @GetMapping("/doctor/schedules")
    public ResponseEntity<ApiResponse<Page<ScheduleDTOProjection>>> getAllMySchedules(Integer userId,
                                                                                      @RequestParam(defaultValue = "0")int pageNumber,
                                                                                      @RequestParam(defaultValue = "5")int pageSize){

        User user = jwtUtils.getLoggedInUser();
        userId = user.getId();

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
    public ResponseEntity<ApiResponse<ScheduleDTOSTR>> getScheduleById(@PathVariable Integer scheduleId){

        return ResponseEntity.ok(new ApiResponse<>("Success",
                scheduleService.getScheduleById(scheduleId)));
    }

    @PutMapping("/doctor/edit-schedule/{scheduleId}")
    public ResponseEntity<ApiResponse<String>> updateSchedule(@PathVariable Integer scheduleId,
                                                              @RequestBody ScheduleDTOSTR scheduleDTO)
    {

            scheduleService.updateSchedule(scheduleId, scheduleDTO);

        return ResponseEntity.ok(new ApiResponse<>("Schedule Updated Success"));

    }




}
