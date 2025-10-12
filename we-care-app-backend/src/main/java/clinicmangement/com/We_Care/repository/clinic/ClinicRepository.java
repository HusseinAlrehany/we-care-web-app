package clinicmangement.com.We_Care.repository.clinic;

import clinicmangement.com.We_Care.DTO.ClinicDTOProjection;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsInfoProjection;
import clinicmangement.com.We_Care.models.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
    //query with DTO projection to save the result
    //to be fixed speciality details not shown in front end
    //also it needs additional count query for paging support
    @Query(value = """
            SELECT c.address AS address,
            c.clinic_mobile AS clinicMobile,
            d.first_name AS firstName,
            d.last_name AS lastName,
            s.state_name AS stateName,
            ct.city_name AS cityName,
            u.email AS doctorEmail,
            sp.name AS specialityName
            
            FROM clinic c 
            INNER JOIN doctors d ON c.doctor_id = d.id
            INNER JOIN states s ON  c.state_id = s.id
            INNER JOIN cities ct ON c.city_id = ct.id
            INNER JOIN users u ON u.id = d.user_id
            INNER JOIN speciality sp ON sp.id = d.speciality_id
            
            WHERE d.speciality_id = :specialityId
            """, nativeQuery = true)
    Page<SpecialityDetailsInfoProjection> getSpecialityDetailsInfo(@Param("specialityId")Integer specialityId, Pageable pageable);


    @Query(value = """
            SELECT c.id AS clinicId,
            CONCAT (c.address, ' _ ', ct.city_name, ' _ ', st.state_name) AS clinicAddress
            FROM clinic c
            INNER JOIN doctors d
            ON c.doctor_id = d.id
            INNER JOIN states st
            ON c.state_id = st.id
            INNER JOIN cities ct
            ON c.city_id = ct.id
            WHERE d.user_id = :userId
            """,nativeQuery = true)
    List<ClinicDTOProjection> getAllClinicByUserId(@Param("userId") Integer userId);

}
