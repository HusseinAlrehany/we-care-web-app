package clinicmangement.com.We_Care.repository.reviews;

import clinicmangement.com.We_Care.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
}
