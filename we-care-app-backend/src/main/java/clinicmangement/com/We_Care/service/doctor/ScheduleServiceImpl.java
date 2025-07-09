package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ScheduleDTO;
import clinicmangement.com.We_Care.DTO.ScheduleDTOProjection;
import clinicmangement.com.We_Care.DTO.ScheduleDTOSTR;
import clinicmangement.com.We_Care.DTO.ScheduleViewProjection;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.exceptions.types.OverLappedSchedulesException;
import clinicmangement.com.We_Care.mapper.ScheduleMapper;

import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.repository.clinic.ClinicRepository;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.schedule.ScheduleAppointmentRepository;

import clinicmangement.com.We_Care.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createDailySchedule(ScheduleDTO scheduleDTO, Integer userId) {

        Doctor doctor = doctorRepository.findDoctorByUserId(userId);

        if(doctor == null){
            throw new NotFoundException("No Doctor Found For userId: " + userId);
        }

        Clinic clinic = clinicRepository.findById(scheduleDTO.getClinicId())
                .orElseThrow(() -> new NotFoundException("Clinic not found"));

        //Truncating the time before calling the query method
        LocalTime startTime = scheduleDTO.getStartTime().truncatedTo(ChronoUnit.MINUTES);
        LocalTime  endTime = scheduleDTO.getEndTime().truncatedTo(ChronoUnit.MINUTES);

        List<ScheduleAppointment> overlappedSchedules = scheduleRepository.findOverlappedSchedules(
                doctor.getId(),
                scheduleDTO.getDate(),
                startTime,
                endTime);

        if(!overlappedSchedules.isEmpty()){
            throw new OverLappedSchedulesException("This Schedule is overlapping with other existing Schedules ");
        }

        ScheduleAppointment scheduleAppointment = scheduleMapper.toEntity(scheduleDTO);

        scheduleAppointment.setClinic(clinic);
        scheduleAppointment.setDoctor(doctor);


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
    public ScheduleDTOSTR getScheduleById(Integer scheduleId) {
        ScheduleAppointment scheduleAppointment =
                scheduleRepository.findById(scheduleId)
                        .orElseThrow(()-> new NotFoundException("No Schedule Found ID " + scheduleId));


        return scheduleMapper.toDTOSTR(scheduleAppointment);
    }

    @Override
    public void updateSchedule(Integer scheduleId, ScheduleDTOSTR scheduleDTO) {
        ScheduleAppointment scheduleAppointment =
                scheduleRepository.findById(scheduleId)
                        .orElseThrow(()-> new NotFoundException("No Schedule Found ID " + scheduleId));

        scheduleMapper.updateScheduleEntityFromDTO(scheduleAppointment, scheduleDTO);

        scheduleRepository.save(scheduleAppointment);

    }



}
