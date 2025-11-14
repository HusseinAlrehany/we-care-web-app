package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.DoctorDTO;
import clinicmangement.com.WeCare.DTO.DoctorDTODetails;
import clinicmangement.com.WeCare.DTO.ShortDoctorDTO;
import clinicmangement.com.WeCare.enums.StateName;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.mapper.DoctorDTODetailsMapper;
import clinicmangement.com.WeCare.mapper.DoctorMapper;
import clinicmangement.com.WeCare.mapper.ShortDoctorDTOMapper;
import clinicmangement.com.WeCare.models.Doctor;
import clinicmangement.com.WeCare.models.Speciality;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.speciality.SpecialityRepository;
import clinicmangement.com.WeCare.repository.user.UserRepository;
import clinicmangement.com.WeCare.search.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminDoctorServiceImpl implements AdminDoctorService{

    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;

    private final SpecialityRepository specialityRepository;

    private final ShortDoctorDTOMapper shortDoctorDTOMapper;

    private final DoctorMapper doctorMapper;

    private final DoctorDTODetailsMapper doctorDTODetailsMapper;

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


    //uses JPQL query for filtering but not work as intended
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

    @Override
    public DoctorDTO getDoctorById(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No Doctor Found ID: " + id));

        return doctorMapper.toDTO(doctor);
    }

    @Override
    public DoctorDTODetails updateDoctor(Integer id, DoctorDTODetails doctorDTODetails) {

        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No Doctor Found"));

        Speciality speciality = specialityRepository.findById(doctorDTODetails.getSpecialityId())
                .orElseThrow(()-> new NotFoundException("No Speciality Found"));

        User user = userRepository.findById(existingDoctor.getUser().getId())
                .orElseThrow(()-> new NotFoundException("No User Found"));


        doctorDTODetailsMapper.updateDoctorEntityFromDTO(existingDoctor, doctorDTODetails);

        existingDoctor.setSpeciality(speciality);
        existingDoctor.setUser(user);

        return doctorDTODetailsMapper.toDoctorDTODetails(doctorRepository.save(existingDoctor));
    }
}
