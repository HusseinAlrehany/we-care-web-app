package clinicmangement.com.We_Care.repository.visit;

import clinicmangement.com.We_Care.DTO.PatientBookedVisitsProjection;
import clinicmangement.com.We_Care.enums.VisitStatus;
import clinicmangement.com.We_Care.models.VisitBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface VisitBookingRepository extends JpaRepository<VisitBooking, Integer> {



   VisitBooking findByPatientMobileAndScheduleId(String patientMobile, Integer scheduleId);


   @Modifying
   @Transactional
   @Query("""
           UPDATE VisitBooking vb
           SET vb.visitStatus = 'CHECKED'
           WHERE vb.schedule.scheduleStatus = 'INACTIVE'
           AND vb.schedule.date < :today
           """)
   int  markPastVisitsAsChecked(@Param("today")LocalDate today);


   @Query(value = """
           SELECT vb.patient_name AS patientName,
                  vb.patient_mobile AS patientMobile,
                  scp.date AS date,
                  sp.name AS specialityName,
                  CONCAT(d.first_name, ' ', d.last_name) AS doctorName,
                  CONCAT(c.address, ' ', st.state_name, ' ', ct.city_name) AS fullAddress
                  
                  FROM visit_booking vb
                  INNER JOIN clinic c
                  ON c.id = vb.clinic_id
                  INNER JOIN doctors d
                  ON d.id = vb.doctor_id
                  INNER JOIN speciality sp
                  ON sp.id = d.speciality_id
                  INNER JOIN schedule_appointment scp
                  ON scp.id = vb.schedule_id
                  INNER JOIN users us
                  ON us.id = vb.user_id
                  INNER JOIN states st
                  ON st.id = c.state_id
                  INNER JOIN cities ct
                  ON ct.id = c.city_id
                  
                  WHERE us.id = :userId AND vb.visit_status = :status
           """, nativeQuery = true)
   List<PatientBookedVisitsProjection> getPatientBookedVisits(@Param("userId") Integer userId,
                                                              //pass enum as string in the repository method
                                                              //to avoid native query issues
                                                              @Param("status")String status );

}
