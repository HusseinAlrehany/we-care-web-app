package clinicmangement.com.WeCare.DTO;

import java.time.LocalDate;

public interface PatientBookedVisitsProjection {

    Integer getDoctorId();
    String getPatientName();
    String getPatientMobile();
    LocalDate getDate();
    String getDoctorName();
    String getSpecialityName();
    String getFullAddress();

}
