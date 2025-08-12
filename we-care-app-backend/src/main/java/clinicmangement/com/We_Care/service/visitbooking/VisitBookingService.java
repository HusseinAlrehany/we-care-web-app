package clinicmangement.com.We_Care.service.visitbooking;

import clinicmangement.com.We_Care.DTO.BookedDoctorDTOProjection;
import clinicmangement.com.We_Care.DTO.VisitBookingDTO;
import clinicmangement.com.We_Care.models.User;

public interface VisitBookingService {
    void bookVisit(VisitBookingDTO visitBookingDTO, User currentUser);

    BookedDoctorDTOProjection getBookedDoctorWithSchedule(Integer scheduleId);
}
