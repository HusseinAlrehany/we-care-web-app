package clinicmangement.com.We_Care.repository.visit;

import clinicmangement.com.We_Care.models.VisitBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitBookingRepository extends JpaRepository<VisitBooking, Integer> {



   VisitBooking findByPatientMobileAndScheduleId(String patientMobile, Integer scheduleId);
}
