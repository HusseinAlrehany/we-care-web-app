package clinicmangement.com.WeCare.firebase.notificationservice;

import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.models.VisitBooking;

public interface NotificationService {

    void sendBookingNotification(VisitBooking booking);

    void saveOrUpdateNotificationToken(String request, User user);
}
