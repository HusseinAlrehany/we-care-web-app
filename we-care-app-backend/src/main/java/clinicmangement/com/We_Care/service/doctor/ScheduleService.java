package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ScheduleDTORead;
import clinicmangement.com.We_Care.DTO.ScheduleDTOProjection;
import clinicmangement.com.We_Care.DTO.ScheduleDTOWrite;
import org.springframework.data.domain.Page;

public interface ScheduleService {

    void createDailySchedule(ScheduleDTOWrite scheduleDTOWrite, Integer userId);

    Page<ScheduleDTOProjection> findAllSchedulesByUserId(Integer userId, int pageNumber, int pageSize);

    void deleteScheduleById(Integer scheduleId);


    void updateSchedule(Integer scheduleId, ScheduleDTOWrite scheduleDTOWrite);

    ScheduleDTORead getScheduleById(Integer scheduleId);
}
