package clinicmangement.com.We_Care.firebase.notificationservice;

import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.firebase.repository.NotificationTokenRepository;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.NotificationToken;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.models.VisitBooking;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final DoctorRepository doctorRepository;
    private final NotificationTokenRepository notificationTokenRepository;

    @Override
    public void saveOrUpdateNotificationToken(String notificationToken, User user) {


        if(notificationToken == null || notificationToken.isBlank()){
            throw new IllegalArgumentException("Notification Token is required");
        }

        Doctor doctor = doctorRepository.findDoctorByUserId(user.getId());

        if(doctor == null){
            throw new NotFoundException("No doctor found for ID: " + user.getId());
        }

          NotificationToken existingToken = notificationTokenRepository.findByNotificationToken(notificationToken)
                  .orElse(null);
          if(existingToken == null){
              NotificationToken token = new NotificationToken();
              token.setDoctor(doctor);
              token.setNotificationToken(notificationToken);


              notificationTokenRepository.save(token);
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
