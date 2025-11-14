package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.ScheduleDTORead;
import clinicmangement.com.WeCare.DTO.ScheduleDTOWrite;
import clinicmangement.com.WeCare.models.ScheduleAppointment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class ScheduleMapper {

    public ScheduleAppointment toEntity(ScheduleDTOWrite scheduleDTOWrite){

        if(scheduleDTOWrite == null){
            throw new NullPointerException("Schedule DTO is null");
        }

        ScheduleAppointment doctorSchedule = new ScheduleAppointment();
        doctorSchedule.setStartTime(LocalTime.parse(scheduleDTOWrite.getStartTime()));
        doctorSchedule.setEndTime(LocalTime.parse(scheduleDTOWrite.getEndTime()));
        doctorSchedule.setDate(LocalDate.parse(scheduleDTOWrite.getDate()));


        return doctorSchedule;
    }


    public ScheduleDTORead toScheduleDTORead(ScheduleAppointment scheduleAppointment){

        if(scheduleAppointment == null){
            throw new NullPointerException("Schedule  is null");
        }

        ScheduleDTORead scheduleDTORead = new ScheduleDTORead();
        scheduleDTORead.setId(scheduleAppointment.getId());

        scheduleDTORead.setDate(scheduleAppointment.getDate());
        scheduleDTORead.setStartTime(scheduleAppointment.getStartTime());
        scheduleDTORead.setEndTime(scheduleAppointment.getEndTime());
        scheduleDTORead.setClinicId(scheduleAppointment.getClinic().getId());


        return scheduleDTORead;
    }

    public ScheduleDTOWrite toScheduleDTOWrite(ScheduleAppointment scheduleAppointment){

        if(scheduleAppointment == null){
            throw new NullPointerException("Schedule  is null");
        }

        ScheduleDTOWrite scheduleDTOWrite = new ScheduleDTOWrite();
        scheduleDTOWrite.setId(scheduleAppointment.getId());
        scheduleDTOWrite.setDate(scheduleAppointment.getDate().toString());
        scheduleDTOWrite.setStartTime(scheduleAppointment.getStartTime().toString());
        scheduleDTOWrite.setEndTime(scheduleAppointment.getEndTime().toString());

        return scheduleDTOWrite;
    }


    //parsing the date and time to LocalDate, LocalTime
    //before saving to the database
    public void updateScheduleEntityFromDTO(ScheduleAppointment scheduleAppointment,
                                            ScheduleDTOWrite scheduleDTOWrite){
        scheduleAppointment.setDate(LocalDate.parse(scheduleDTOWrite.getDate()));
        scheduleAppointment.setStartTime(LocalTime.parse(scheduleDTOWrite.getStartTime()));
        scheduleAppointment.setEndTime(LocalTime.parse(scheduleDTOWrite.getEndTime()));

    }


}
