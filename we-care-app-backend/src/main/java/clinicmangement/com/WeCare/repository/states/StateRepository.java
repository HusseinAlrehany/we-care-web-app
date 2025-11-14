package clinicmangement.com.WeCare.repository.states;

import clinicmangement.com.WeCare.models.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<States, Integer> {


}
