package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.DoctorDTO;
import clinicmangement.com.WeCare.DTO.DoctorDTODetails;
import clinicmangement.com.WeCare.DTO.ShortDoctorDTO;

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
