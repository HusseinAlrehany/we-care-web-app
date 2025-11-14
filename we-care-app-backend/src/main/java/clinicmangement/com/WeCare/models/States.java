package clinicmangement.com.WeCare.models;

import clinicmangement.com.WeCare.enums.StateName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "states")
@Data
public class States {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @OneToMany(mappedBy = "state")
    private List<Clinic> clinics;

    @OneToMany(mappedBy = "state")
    private List<Cities> cities;
}
