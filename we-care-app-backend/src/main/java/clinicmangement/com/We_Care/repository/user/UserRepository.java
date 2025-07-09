package clinicmangement.com.We_Care.repository.user;

import clinicmangement.com.We_Care.enums.UserRole;
import clinicmangement.com.We_Care.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findFirstByEmail(String userName);

    User findByUserRole(UserRole userRole);

    @Query(value = """
            SELECT u.* FROM users u
            JOIN doctors d
            ON u.id = d.user_id
            WHERE u.email = :email
            """,
            nativeQuery = true
          )
    Optional<User> findByEmailWithDoctor(@Param("email") String email);
}
