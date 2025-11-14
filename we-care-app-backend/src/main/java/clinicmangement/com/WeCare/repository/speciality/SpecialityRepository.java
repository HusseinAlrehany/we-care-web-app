package clinicmangement.com.WeCare.repository.speciality;

import clinicmangement.com.WeCare.models.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    boolean existsByNameIgnoreCase(String specialityName);
}
