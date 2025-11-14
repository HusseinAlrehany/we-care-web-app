package clinicmangement.com.WeCare.service.doctor;

import clinicmangement.com.WeCare.DTO.ScheduleDTORead;
import clinicmangement.com.WeCare.DTO.ScheduleDTOProjection;
import clinicmangement.com.WeCare.DTO.ScheduleDTOWrite;
import org.springframework.data.domain.Page;

public interface ScheduleService {

    void createDailySchedule(ScheduleDTOWrite scheduleDTOWrite, Integer userId);

    Page<ScheduleDTOProjection> findAllSchedulesByUserId(Integer userId, int pageNumber, int pageSize);

    void deleteScheduleById(Integer scheduleId);


    void updateSchedule(Integer scheduleId, ScheduleDTOWrite scheduleDTOWrite);

    ScheduleDTORead getScheduleById(Integer scheduleId);
}
