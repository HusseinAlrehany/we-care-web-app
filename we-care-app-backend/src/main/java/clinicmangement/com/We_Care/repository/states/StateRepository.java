package clinicmangement.com.We_Care.repository.states;

import clinicmangement.com.We_Care.models.Cities;
import clinicmangement.com.We_Care.models.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<States, Integer> {


}
