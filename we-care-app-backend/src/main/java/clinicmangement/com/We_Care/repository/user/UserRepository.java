package clinicmangement.com.We_Care.repository.user;

import clinicmangement.com.We_Care.DTO.UserProfileDTOProjection;
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

    //Spring data jpa does not automatically map native query result to class constructor(constructor DTO projection)
    //but it requires extra setup to achieve that
    //so we will use JPQL as it is best fit here
    //since we need to map images URLS in the constructor
    //the (new) keyword used in the query because of mapping the result into custom DTO
    //and not in an entity, if so no need to use new
    @Query("""
            SELECT new clinicmangement.com.We_Care.DTO.UserProfileDTOProjection
            (d.briefIntroduction, d.fees, d.id)
            FROM Doctor d
            JOIN d.user u
            WHERE u.id = :userId 
            """)
    UserProfileDTOProjection findProfileById(@Param("userId") Integer userId);
}
