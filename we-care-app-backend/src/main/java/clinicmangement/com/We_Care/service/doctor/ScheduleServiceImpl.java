package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ScheduleDTORead;
import clinicmangement.com.We_Care.DTO.ScheduleDTOProjection;
import clinicmangement.com.We_Care.DTO.ScheduleDTOWrite;
import clinicmangement.com.We_Care.enums.ScheduleStatus;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.exceptions.types.OverLappedSchedulesException;
import clinicmangement.com.We_Care.mapper.ScheduleMapper;

import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import clinicmangement.com.We_Care.repository.clinic.ClinicRepository;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.schedule.ScheduleAppointmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleAppointmentRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    @Override
    public void createDailySchedule(ScheduleDTOWrite scheduleDTOWrite, Integer userId) {

        Doctor doctor = doctorRepository.findDoctorByUserId(userId);

        if(doctor == null){
            throw new NotFoundException("No Doctor Found For userId: " + userId);
        }

        Clinic clinic = clinicRepository.findById(scheduleDTOWrite.getClinicId())
                .orElseThrow(() -> new NotFoundException("Clinic not found"));

        //overlapping logic
        LocalTime startTime = LocalTime.parse(scheduleDTOWrite.getStartTime()).truncatedTo(ChronoUnit.MINUTES);
        LocalTime  endTime = LocalTime.parse(scheduleDTOWrite.getEndTime()).truncatedTo(ChronoUnit.MINUTES);
        LocalDate date = LocalDate.parse(scheduleDTOWrite.getDate());

        LocalDateTime startDateTime = date.atTime(startTime);
        LocalDateTime endDateTime = endTime.isBefore(startTime)
                ? date.plusDays(1).atTime(endTime)
                : date.atTime(endTime);


        List<ScheduleAppointment> allSchedules = scheduleRepository.findAllByDoctor_IdAndDate(doctor.getId(), date);

        for (ScheduleAppointment existing : allSchedules) {
            LocalDateTime existingStart = date.atTime(existing.getStartTime());
            LocalDateTime existingEnd = existing.getEndTime().isBefore(existing.getStartTime())
                    ? date.plusDays(1).atTime(existing.getEndTime())
                    : date.atTime(existing.getEndTime());

            if (startDateTime.isBefore(existingEnd) && endDateTime.isAfter(existingStart)) {
                throw new OverLappedSchedulesException("This Schedule is overlapping with other existing Schedules");
            }


        }

        //overlapping logic ends here
        ScheduleAppointment scheduleAppointment = scheduleMapper.toEntity(scheduleDTOWrite);

        scheduleAppointment.setClinic(clinic);
        scheduleAppointment.setDoctor(doctor);
        scheduleAppointment.setScheduleStatus(ScheduleStatus.ACTIVE);

        scheduleRepository.save(scheduleAppointment);

    }

    @Override
    public Page<ScheduleDTOProjection> findAllSchedulesByUserId(Integer userId, int pageNumber,
                                                                  int pageSize) {

        Page<ScheduleDTOProjection> projections = scheduleRepository.findAllSchedulesByUserId(userId, PageRequest.of(pageNumber, pageSize));

        if(!projections.hasContent()){
            throw new NotFoundException("No Schedules Created");
        }

        return projections;
    }

    @Override
    public void deleteScheduleById(Integer scheduleId) {

        ScheduleAppointment scheduleAppointment = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NotFoundException("No Schedule Found ID: " + scheduleId));

        scheduleRepository.deleteById(scheduleId);

    }

    @Override
    public ScheduleDTORead getScheduleById(Integer scheduleId) {
        ScheduleAppointment scheduleAppointment =
                scheduleRepository.findById(scheduleId)
                        .orElseThrow(()-> new NotFoundException("No Schedule Found ID " + scheduleId));


        return scheduleMapper.toScheduleDTORead(scheduleAppointment);
    }

    @Override
    public void updateSchedule(Integer scheduleId, ScheduleDTOWrite scheduleDTOWrite) {
        ScheduleAppointment scheduleAppointment =
                scheduleRepository.findById(scheduleId)
                        .orElseThrow(()-> new NotFoundException("No Schedule Found ID " + scheduleId));

        scheduleMapper.updateScheduleEntityFromDTO(scheduleAppointment, scheduleDTOWrite);

        scheduleRepository.save(scheduleAppointment);

    }

    //this function runs every day at 03:00 PM
    @Scheduled(cron = "0 55 19 * * *", zone = "Africa/Cairo")
    public void deactivateExpiredSchedules(){
        //LocalDate today = LocalDate.now();
        int updatedRows = scheduleRepository.deactivatedExpiredSchedules(LocalDate.now());

        if(updatedRows > 0){
           System.out.println("DEACTIVATED " + updatedRows + " expired appointment");
        }
    }


    //cron details
   //0 0 15 * * *

    //0 -> seconds
    //0 -> minute
    //15 -> hour
    //* -> day of month(any)
    //* -> month(any)
    //* -> day of week(any)


}
