package clinicmangement.com.We_Care.repository.doctor;

import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
}
