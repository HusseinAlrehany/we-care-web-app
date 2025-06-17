package clinicmangement.com.We_Care.repository.speciality;

import clinicmangement.com.We_Care.models.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    boolean existsByNameIgnoreCase(String specialityName);
}
