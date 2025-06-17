package clinicmangement.com.We_Care.repository.schedule;

import clinicmangement.com.We_Care.models.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {
}
