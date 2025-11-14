package clinicmangement.com.WeCare.service.visitbooking;

import clinicmangement.com.WeCare.DTO.BookedDoctorDTOProjection;
import clinicmangement.com.WeCare.DTO.VisitBookingDTO;
import clinicmangement.com.WeCare.models.User;

public interface VisitBookingService {
    void bookVisit(VisitBookingDTO visitBookingDTO, User currentUser);

    BookedDoctorDTOProjection getBookedDoctorWithSchedule(Integer scheduleId);
}
