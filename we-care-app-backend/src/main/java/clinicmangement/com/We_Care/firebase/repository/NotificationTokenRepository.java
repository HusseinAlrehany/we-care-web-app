package clinicmangement.com.We_Care.firebase.repository;

import clinicmangement.com.We_Care.models.NotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {


    List<NotificationToken> findByDoctorId(Integer doctorId);

    Optional<NotificationToken> findByNotificationToken(String token);

    void deleteByNotificationToken(String token);

    Optional<NotificationToken> findByDoctor_Id(Integer id);
}
