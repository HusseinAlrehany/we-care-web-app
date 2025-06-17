package clinicmangement.com.We_Care.repository.clinic;

import clinicmangement.com.We_Care.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
}
