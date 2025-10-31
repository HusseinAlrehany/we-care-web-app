package clinicmangement.com.We_Care.models;

import clinicmangement.com.We_Care.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Schedule_appointment")
@Data
public class ScheduleAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // when doctor(parent) deleted , delete the child (schedule appointment) too
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Clinic clinic;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus scheduleStatus;

}
