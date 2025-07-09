package clinicmangement.com.We_Care.repository.doctor;

import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {


    @Query("SELECT d FROM Doctor d " +
            "JOIN FETCH d.speciality s " +
            "JOIN FETCH d.clinicList c " +
            "JOIN FETCH c.city ct " +
            "JOIN FETCH c.state st " +
            "WHERE (:specialityName IS NULL OR s.name = :specialityName) " +
            "AND (:doctorName IS NULL OR d.firstName = :doctorName) " +
            "AND (:cityName IS NULL OR ct.cityName = :cityName) " +
            "AND (:stateName IS NULL OR st.stateName = :stateName)")
    List<Doctor> filterDoctors(@Param("doctorName")String doctorName,
                               @Param("specialityName") String specialityName,
                               @Param("stateName") StateName stateName,
                               @Param("cityName") String cityName);

    Page<Doctor> findAllBySpeciality_Id(Integer specialityId, Pageable pageable);

    Optional<Doctor> findByUser_Id(Integer userId);

    //Using JPQL for automatic pagination support
    //native query need additional count query for pagination
    @Query(value = """
               SELECT d
               FROM Doctor d
               WHERE d.speciality.id = (
                  SELECT doc.speciality.id
                  FROM Doctor doc
                  WHERE doc.user.id = :userId
               )
            """)
    Page<Doctor> findAllSameSpecialityDocs(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = """
            Select d.*
            FROM doctors d
            INNER JOIN users us
            ON d.user_id = us.id
            WHERE us.id = :userId
            """, nativeQuery = true)
    Doctor findDoctorByUserId(@Param("userId") Integer userId);


}
