package clinicmangement.com.We_Care.repository.city;

import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.models.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<Cities, Integer> {

    List<Cities> findAllByState_StateName(StateName stateName);

    List<Cities> findAllByState_Id(Integer stateId);
}
