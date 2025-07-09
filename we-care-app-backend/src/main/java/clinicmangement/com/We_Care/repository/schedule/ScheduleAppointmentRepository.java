package clinicmangement.com.We_Care.repository.schedule;

import clinicmangement.com.We_Care.DTO.ScheduleDTOProjection;
import clinicmangement.com.We_Care.DTO.ScheduleViewProjection;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleAppointmentRepository extends JpaRepository<ScheduleAppointment, Integer> {


    //to avoid overlapping of schedules appointments
    @Query(value = """
            SELECT * FROM schedule_appointment s 
            WHERE s.doctor_id = :doctorId
            AND s.date = :date
            AND (
                  (:startTime < s.end_time AND :endTime > s.start_time)      
                ) 
            """, nativeQuery = true)
    List<ScheduleAppointment> findOverlappedSchedules(
            @Param("doctorId") Integer doctorId,
            @Param("date")LocalDate date,
            @Param("startTime")LocalTime startTime,
            @Param("endTime")LocalTime endTime
            );


    //using DTO projection to avoid loading all the object and it's blobs(images)
    //even if using scheduleDTO.setDoctorId(scheduleAppointment.getDoctor().getId());
    //this will load the entire Doctor and it's blobs and relations which causes outOfMemory error
    @Query(value = """
            SELECT
            s.id AS id,
            s.date AS date,
            s.end_time AS endTime,
            s.start_time AS startTime,
            d.id AS doctorId,
            c.id AS clinicId,
            CONCAT(c.address, ', ', ct.city_name, ', ', st.state_name) AS clinicLocation
            FROM schedule_appointment s
            JOIN doctors d ON s.doctor_id = d.id
            JOIN clinic c ON s.clinic_id = c.id
            JOIN cities ct ON c.city_id = ct.id
            JOIN states st ON c.state_id = st.id
            WHERE s.id = :id
            """,nativeQuery = true)
    Optional<ScheduleViewProjection> findScheduleProjection(@Param("id") Integer id);

    //using JPQL (entities specific) query , with paging support
    @Query("""
            SELECT new clinicmangement.com.We_Care.DTO.ScheduleDTOProjection(
               s.id,
               s.date,
               s.startTime,
               s.endTime,
               CONCAT(c.address, ', ', ct.cityName, ', ', st.stateName)
            )
            
            FROM ScheduleAppointment s
            JOIN s.doctor d
            JOIN s.clinic c
            JOIN c.city ct
            JOIN c.state st
            JOIN d.user u
            
            WHERE u.id = :userId
            """)
    Page<ScheduleDTOProjection> findAllSchedulesByUserId(@Param("userId")Integer userId, Pageable pageable);



}
