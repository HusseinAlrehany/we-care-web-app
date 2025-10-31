package clinicmangement.com.We_Care.controller.notification;

import clinicmangement.com.We_Care.DTO.NotificationTokenRequest;
import clinicmangement.com.We_Care.firebase.notificationservice.NotificationService;
import clinicmangement.com.We_Care.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/we-care")
@PreAuthorize("hasAuthority('DOCTOR')")
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;

    @PostMapping("/doctor/notification-token")
    public ResponseEntity<Void> registerToken(@RequestBody NotificationTokenRequest request,
                                              @AuthenticationPrincipal User user){

        notificationService.saveOrUpdateNotificationToken(request.getNotificationToken(), user);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
