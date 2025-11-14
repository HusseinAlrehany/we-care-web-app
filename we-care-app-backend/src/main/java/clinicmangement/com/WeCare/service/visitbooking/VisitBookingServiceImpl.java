package clinicmangement.com.WeCare.service.visitbooking;

import clinicmangement.com.WeCare.DTO.BookedDoctorDTOProjection;
import clinicmangement.com.WeCare.DTO.VisitBookingDTO;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.exceptions.types.UserAlreadyExistsException;
import clinicmangement.com.WeCare.firebase.notificationservice.NotificationService;
import clinicmangement.com.WeCare.mapper.VisitBookingMapper;
import clinicmangement.com.WeCare.models.*;
import clinicmangement.com.WeCare.repository.clinic.ClinicRepository;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.schedule.ScheduleAppointmentRepository;
import clinicmangement.com.WeCare.repository.visit.VisitBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VisitBookingServiceImpl implements VisitBookingService{

    private final VisitBookingRepository visitBookingRepository;
    private final VisitBookingMapper visitBookingMapper;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleAppointmentRepository scheduleAppointmentRepository;
    private final NotificationService notificationService;

    @Transactional //since we fetching multiple entities from database to gurantee that the whole process will be executed
    @Override
    public void bookVisit(VisitBookingDTO visitBookingDTO, User currentUser) {
       Clinic  clinic = clinicRepository.findById(visitBookingDTO.getClinicId())
                .orElseThrow(()-> new NotFoundException("No Clinic Found ID:" + visitBookingDTO.getClinicId()));
       Doctor doctor = doctorRepository.findById(visitBookingDTO.getDoctorId())
                .orElseThrow(()-> new NotFoundException("No Doctor Found ID:" + visitBookingDTO.getDoctorId()));
       ScheduleAppointment  appointment = scheduleAppointmentRepository.findById(visitBookingDTO.getScheduleId())
                .orElseThrow(()-> new NotFoundException("No Schedule Found ID:" + visitBookingDTO.getScheduleId()));

       VisitBooking existVisitBooking = visitBookingRepository.findByPatientMobileAndScheduleId(
               visitBookingDTO.getPatientMobile(),
               visitBookingDTO.getScheduleId());

        //there is edge case here
        //patient can book visit which has past date
        //before the schedule which will deactivate this date triggered

       if(existVisitBooking != null){
           throw new UserAlreadyExistsException("You have already booked a visit with the same info!");
       }

        VisitBooking visitBooking = visitBookingMapper
                .toEntity(visitBookingDTO);

       //if the user has credentials otherwise the user is guest user.
       if(currentUser != null){
           visitBooking.setUser(currentUser);
       }else {
           visitBooking.setUser(null);
       }

        visitBooking.setDoctor(doctor);
        visitBooking.setClinic(clinic);
        visitBooking.setSchedule(appointment);

        visitBookingRepository.save(visitBooking);
        notificationService.sendBookingNotification(visitBooking);

       //saving both entities the owner and the other side
       //because we not using (cascade=CASCADETYPE.persist) on the owner side
        //if we do so saving the owner will be enough
      //VisitBooking savedVisit = visitBookingRepository.save(visitBooking);
      //setting the visit in the owner side (schedule appointment) not vice versa
      //because the owner side (contains the foreign key) has the ability to control the relation
      //appointment.setVisitBooking(visitBooking);

      //scheduleAppointmentRepository.save(appointment);

    }

    @Override
    public BookedDoctorDTOProjection getBookedDoctorWithSchedule(Integer scheduleId) {

        BookedDoctorDTOProjection bookedDoctorDTOProjection =
                doctorRepository.getBookedDoctor(scheduleId);

        if(bookedDoctorDTOProjection == null){
            throw new NotFoundException("No Info Found For Selected schedule ID: " + scheduleId);
        }

        return bookedDoctorDTOProjection;
    }

    //schedule task done every day at 4:43 PM
    //mark past visits as CHECKED
    @Scheduled(cron = "0 57 19 * * *", zone = "Africa/Cairo")
    public void markPastVisitsAsChecked(){

        int updatedRows = visitBookingRepository.markPastVisitsAsChecked(LocalDate.now());

        if(updatedRows > 0){
            System.out.println("mark as CHECKED " + updatedRows + " Checked ROWS");
        }

    }
}
