package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ScheduleDTO;
import clinicmangement.com.We_Care.DTO.ScheduleDTOProjection;
import clinicmangement.com.We_Care.DTO.ScheduleDTOSTR;
import clinicmangement.com.We_Care.DTO.ScheduleViewProjection;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ScheduleService {

    void createDailySchedule(ScheduleDTO scheduleDTO, Integer userId);

    Page<ScheduleDTOProjection> findAllSchedulesByUserId(Integer userId, int pageNumber, int pageSize);

    void deleteScheduleById(Integer scheduleId);


    void updateSchedule(Integer scheduleId, ScheduleDTOSTR scheduleDTO);

    ScheduleDTOSTR getScheduleById(Integer scheduleId);
}
