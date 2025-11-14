package clinicmangement.com.WeCare.repository.city;

import clinicmangement.com.WeCare.enums.StateName;
import clinicmangement.com.WeCare.models.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<Cities, Integer> {

    List<Cities> findAllByState_StateName(StateName stateName);

    List<Cities> findAllByState_Id(Integer stateId);
}
