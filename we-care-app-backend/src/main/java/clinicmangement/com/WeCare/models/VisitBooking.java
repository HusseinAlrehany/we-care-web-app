package clinicmangement.com.WeCare.models;

import clinicmangement.com.WeCare.enums.VisitStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "visit_booking")
@Data
public class VisitBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String patientName;
    private String patientMobile;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ScheduleAppointment schedule;


    //@OnDelete(action = OnDeleteAction.CASCADE) => this annotation is database level annotation
    //only applied on a parent reference (which contains primary key)
    //and it tells hibernate when that parent deleted, delete it's related child too
    //but cascadeType.remove applied on the child reference and it is persistence provider level
}
