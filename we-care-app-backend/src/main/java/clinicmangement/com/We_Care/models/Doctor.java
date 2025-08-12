package clinicmangement.com.We_Care.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Lob
    private String briefIntroduction;
    private LocalDateTime joiningDate;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] medicalCard;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] doctorPhoto;

    private Integer fees;

    private Double totalRating;

    private Double averageRating;

    //this entity (doctor) is the owner of the relationship
    //because it has @JoinColumn and the foreign key column
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    //@JsonManagedReference
    @OneToMany(mappedBy = "doctor")
    //@JsonIgnore
    private List<ScheduleAppointment> doctorScheduleList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Speciality speciality;


    @OneToMany(mappedBy = "doctor")
    //@JsonIgnore
    private List<Clinic> clinicList;

    @OneToMany(mappedBy = "doctor")
    private List<VisitBooking> visitBookingList;

    //add last updated column to be appended to the img url
    //to help versioning the url and bypass the browser cached img urls
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

}
