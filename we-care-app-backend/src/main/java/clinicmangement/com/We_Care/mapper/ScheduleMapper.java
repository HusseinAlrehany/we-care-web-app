package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.ScheduleDTO;
import clinicmangement.com.We_Care.DTO.ScheduleDTOSTR;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class ScheduleMapper {

    public ScheduleAppointment toEntity(ScheduleDTO scheduleDTO){

        if(scheduleDTO == null){
            throw new NullPointerException("Schedule DTO is null");
        }

        ScheduleAppointment doctorSchedule = new ScheduleAppointment();
        doctorSchedule.setStartTime(scheduleDTO.getStartTime());
        doctorSchedule.setEndTime(scheduleDTO.getEndTime());
        doctorSchedule.setDate(scheduleDTO.getDate());


        return doctorSchedule;
    }


    public ScheduleDTO toDTO(ScheduleAppointment scheduleAppointment){

        if(scheduleAppointment == null){
            throw new NullPointerException("Schedule  is null");
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(scheduleAppointment.getId());

        scheduleDTO.setDate(scheduleAppointment.getDate());
        scheduleDTO.setStartTime(scheduleAppointment.getStartTime());
        scheduleDTO.setEndTime(scheduleAppointment.getEndTime());


        return scheduleDTO;
    }

    public ScheduleDTOSTR toDTOSTR(ScheduleAppointment scheduleAppointment){

        if(scheduleAppointment == null){
            throw new NullPointerException("Schedule  is null");
        }

        ScheduleDTOSTR scheduleDTOSTR = new ScheduleDTOSTR();
        scheduleDTOSTR.setId(scheduleAppointment.getId());
        scheduleDTOSTR.setScDate(scheduleAppointment.getDate().toString());
        scheduleDTOSTR.setScStartTime(scheduleAppointment.getStartTime().toString());
        scheduleDTOSTR.setScEndTime(scheduleAppointment.getEndTime().toString());

        return scheduleDTOSTR;
    }


    //parsing the date and time to LocalDate, LocalTime
    //before saving to the database
    public void updateScheduleEntityFromDTO(ScheduleAppointment scheduleAppointment,
                                            ScheduleDTOSTR scheduleDTOSTR){
        scheduleAppointment.setDate(LocalDate.parse(scheduleDTOSTR.getScDate()));
        scheduleAppointment.setStartTime(LocalTime.parse(scheduleDTOSTR.getScStartTime()));
        scheduleAppointment.setEndTime(LocalTime.parse(scheduleDTOSTR.getScEndTime()));

    }


}
