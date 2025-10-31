package clinicmangement.com.We_Care.repository.reviews;

import clinicmangement.com.We_Care.DTO.ReviewDTOResponseProjection;
import clinicmangement.com.We_Care.models.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    List<Reviews> findByDoctor_Id(Integer id);


    //COALESCE(SUM(r.rating, 0)) to avoid returning null and replace it will 0 instead of null
    //if there is no rating for that doctor instead of return null it will replaced with ZERO.
    @Query(value = """
             SELECT COALESCE(SUM(r.rating), 0) FROM reviews r
             WHERE r.doctor_id = :doctorId
            """,nativeQuery = true)
    Double getTotalRatingByDoctorId(@Param("doctorId")Integer doctorId);


    @Query(value = """
            SELECT COALESCE(AVG(r.rating), 0) FROM reviews r
            WHERE r.doctor_id = :doctorId
            """, nativeQuery = true)
    Double getAverageRatingByDoctorId(@Param("doctorId")Integer doctorId);

    @Query(value = """
            SELECT COALESCE(COUNT(r.rating), 0) FROM reviews r
            WHERE r.doctor_id = :doctorId
            """,nativeQuery = true)
    Integer getNumberOfReviewsByDoctorId(@Param("doctorId")Integer doctorId);





    @Query(value = """
            SELECT r.id AS reviewId,
                   d.id AS doctorId,
                   r.created_at AS createdAt,
                   COALESCE(d.total_rating, 0) AS totalRating,
                   COALESCE(d.average_rating, 0) AS averageRating,
                   COALESCE(r.rating, 0) AS rating,
                   r.comment AS comment,
                   CONCAT (u.first_name, ' ', u.last_name) AS patientName
                   
                   FROM reviews r
                   INNER JOIN doctors d
                   ON r.doctor_id = d.id
                   INNER JOIN users u
                   ON u.id = r.user_id
                   WHERE d.id = :doctorId
                   ORDER BY r.created_at DESC 
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM reviews r
                    INNER JOIN doctors d ON r.doctor_id = d.id
                    INNER JOIN users u ON u.id = r.user_id
                    WHERE d.id = :doctorId
                    
                    """, nativeQuery = true
    )
    Page<ReviewDTOResponseProjection> getReviewsByDoctorId(@Param("doctorId")Integer doctorId,
                                                           Pageable pageable);
}
