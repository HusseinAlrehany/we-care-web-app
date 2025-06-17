package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.DoctorDTO;
import clinicmangement.com.We_Care.DTO.ShortDoctorDTO;
import clinicmangement.com.We_Care.enums.StateName;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.DoctorMapper;
import clinicmangement.com.We_Care.mapper.ShortDoctorDTOMapper;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.user.UserRepository;
import clinicmangement.com.We_Care.search.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminDoctorServiceImpl implements AdminDoctorService{

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    private final UserRepository userRepository;

    private final ShortDoctorDTOMapper shortDoctorDTOMapper;

    @Override
    public List<DoctorDTO> findAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found!!");
        }

        return doctorMapper.toDoctorDTOList(doctors);
    }

    @Override
    public void deleteDoctor(Integer id) {
       Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
       if(optionalDoctor.isEmpty()){
           throw new NotFoundException("No Doctor Found For ID:" + id);
       }

       //delete the base user of doctor to delete doctor account with it.
        userRepository.deleteById(optionalDoctor.get().getUser().getId());
    }

    @Override
    public List<DoctorDTO> searchDoctors(String doctorName, String specialityName, String stateName, String cityName) {

        Specification<Doctor> specification = Specification
                .where(DoctorSpecification.hasName(doctorName))
                .and(DoctorSpecification.hasSpeciality(specialityName))
                .and(DoctorSpecification.hasState(stateName))
                .and(DoctorSpecification.hasCity(cityName));

        List<Doctor> doctors = doctorRepository.findAll(specification);

        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors matches this criteria");
        }

        return doctorMapper.toDoctorDTOList(doctors);
    }
    @Override
    public List<DoctorDTO> filterDoctors( String doctorName,
                                          String specialityName,
                                          String stateName,
                                          String cityName){


        StateName stateName1 = StateName.fromStringToStateEnum(stateName);




        List<Doctor> filteredDoctors = doctorRepository.filterDoctors(doctorName,
                                                                     specialityName,
                                                                      stateName1,
                                                                       cityName);

        if(filteredDoctors.isEmpty()){
            throw new NotFoundException("No Doctors Found");
        }

        return doctorMapper.toDoctorDTOList(filteredDoctors);

    }

    @Override
    public List<ShortDoctorDTO> getAllShortDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found");
        }

        return shortDoctorDTOMapper.toShortDoctorDTOList(doctors);
    }
}
