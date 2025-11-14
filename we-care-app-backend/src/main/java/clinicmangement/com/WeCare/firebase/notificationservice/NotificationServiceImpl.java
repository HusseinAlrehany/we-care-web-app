package clinicmangement.com.WeCare.firebase.notificationservice;

import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.firebase.repository.NotificationTokenRepository;
import clinicmangement.com.WeCare.models.Doctor;
import clinicmangement.com.WeCare.models.NotificationToken;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.models.VisitBooking;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final DoctorRepository doctorRepository;
    private final NotificationTokenRepository notificationTokenRepository;

    @Override
    public void saveOrUpdateNotificationToken(String notificationToken, User user) {
        //firebase notification is per device or per browser notification
        //which means one notification is created and registered for that browser or device
        //if someone else logged in with the same browser the same notification token is saved for that different user
        //unless we handle this situation
        if(notificationToken == null || notificationToken.isBlank()){
            throw new IllegalArgumentException("Notification Token is required");
        }

        Doctor doctor = doctorRepository.findDoctorByUserId(user.getId());

        if(doctor == null){
            throw new NotFoundException("No doctor found for ID: " + user.getId());
        }
        NotificationToken existingToken = notificationTokenRepository.findByDoctor_Id(doctor.getId())
                .orElse(null);

        if(existingToken == null){
            //no token for that doctor then create new one
            NotificationToken token = new NotificationToken();
            token.setDoctor(doctor);
            token.setNotificationToken(notificationToken);
            notificationTokenRepository.save(token);
            //if there is token but no equal to the sent one then update it
        } else if(!existingToken.getNotificationToken().equals(notificationToken)) {
             existingToken.setNotificationToken(notificationToken);
             notificationTokenRepository.save(existingToken);
        }
    }


    @Override
    public void sendBookingNotification(VisitBooking booking) {

        List<NotificationToken> tokens = notificationTokenRepository.findByDoctorId(booking.getDoctor().getId());

        for (NotificationToken t : tokens) {
            Message message = Message.builder()
                    .setToken(t.getNotificationToken())
                    .setNotification(Notification.builder()
                            .setTitle("New Visit Booking")
                            .setBody(booking.getPatientName() + " booked for " +
                                    booking.getSchedule().getDate().toString())
                            .build())
                    .putData("bookingId", String.valueOf(booking.getId()))
                    .putData("doctorId", String.valueOf(booking.getDoctor().getId()))
                    .build();
            try {
                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("Notification Token: " + t.getNotificationToken());
                System.out.println("Sent message: " + response);
            } catch (FirebaseMessagingException e) {

                if(e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED
                        || e.getMessagingErrorCode() == MessagingErrorCode.INVALID_ARGUMENT){
                    notificationTokenRepository.deleteByNotificationToken(t.getNotificationToken());
                }

                System.err.println("Error sending FCM message: " + e.getMessage());

            }
        }


    }
}
