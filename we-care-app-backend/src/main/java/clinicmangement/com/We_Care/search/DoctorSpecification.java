package clinicmangement.com.We_Care.search;


import clinicmangement.com.We_Care.models.Doctor;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecification {

    public static Specification<Doctor> hasName(String doctorName){

        return (root, query,cb) -> {
            if(doctorName == null || doctorName.isBlank()) return null;

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), "%" + doctorName.toLowerCase()),
                    cb.like(cb.lower(root.get("lastName")), "%" + doctorName.toLowerCase())
            );


        };
    }

    public static Specification<Doctor> hasSpeciality(String specialityName){

        return (root, query, cb) -> {
            if(specialityName == null || specialityName.isBlank()) return null;


            return cb.equal(root.join("speciality").get("name"), specialityName);
        };
    }

    public static Specification<Doctor> hasState(String stateName){
        return (root, query, cb) -> {
            if(stateName == null || stateName.isBlank()) return null;

            return cb.equal(root.join("clinicList").join("state").get("stateName"), stateName);
        };
    }

    public static Specification<Doctor> hasCity(String cityName){
        return (root, query,cb)-> {
            if(cityName == null || cityName.isBlank()) return null;

            return cb.equal(root.join("clinicList").join("city").get("cityName"), cityName);
        };
    }
}
