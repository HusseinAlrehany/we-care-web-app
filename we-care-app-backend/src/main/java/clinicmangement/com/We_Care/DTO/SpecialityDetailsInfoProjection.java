package clinicmangement.com.We_Care.DTO;

//DTO projection for query method in clinic repository
//to assign the result in that DTO and no custom mapping needed
public interface SpecialityDetailsInfoProjection {

    String getAddress();
    String getClinicMobile();
    String getFirstName();
    String getLastName();
    String getStateName();
    String getCityName();
    String getDoctorEmail();
    String getSpecialityName();
}
