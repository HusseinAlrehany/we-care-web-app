package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.DTO.DoctorDTODetails;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.enums.StateName;

import java.util.List;

public interface AdminDoctorService {

    List<DoctorDTO> findAllDoctors();

    void deleteDoctor(Integer id);

    List<DoctorDTO> searchDoctors(String doctorName,
                                  String specialityName,
                                  String stateName,
                                  String cityName);


    List<DoctorDTO> filterDoctors( String doctorName,
                                   String specialityName,
                                   String stateName,
                                   String cityName);

    List<ShortDoctorDTO> getAllShortDoctors();

    DoctorDTO getDoctorById(Integer id);

    DoctorDTODetails updateDoctor(Integer id, DoctorDTODetails doctorDTODetails);
}
