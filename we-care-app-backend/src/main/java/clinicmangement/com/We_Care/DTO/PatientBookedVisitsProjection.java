package clinicmangement.com.We_Care.DTO;

import java.time.LocalDate;

public interface PatientBookedVisitsProjection {

    String getPatientName();
    String getPatientMobile();
    LocalDate getDate();
    String getDoctorName();
    String getSpecialityName();
    String getFullAddress();

}
