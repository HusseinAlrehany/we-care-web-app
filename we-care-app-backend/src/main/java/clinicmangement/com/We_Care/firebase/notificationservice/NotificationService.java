package clinicmangement.com.We_Care.firebase.notificationservice;

import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.models.VisitBooking;

public interface NotificationService {

    void sendBookingNotification(VisitBooking booking);

    void saveOrUpdateNotificationToken(String request, User user);
}
