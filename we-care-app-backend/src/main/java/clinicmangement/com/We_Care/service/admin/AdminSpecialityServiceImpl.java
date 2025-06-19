package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.SpecialityDTO;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.We_Care.DTO.SpecialityDetailsInfo;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.exceptions.types.UserAlreadyExistsException;
import clinicmangement.com.We_Care.mapper.ShortDoctorDTOMapper;
import clinicmangement.com.We_Care.mapper.SpecialityDetailsMapper;
import clinicmangement.com.We_Care.mapper.SpecialityMapper;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.Speciality;
import clinicmangement.com.We_Care.repository.clinic.ClinicRepository;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.speciality.SpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSpecialityServiceImpl implements AdminSpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;
    private final SpecialityDetailsMapper specialityDetailsMapper;

    private final DoctorRepository doctorRepository;

    private final ClinicRepository clinicRepository;

    private final ShortDoctorDTOMapper shortDoctorDTOMapper;
    @Override
    public SpecialityDTO addSpeciality(SpecialityDTO specialityDTO) {

         if(specialityRepository.existsByNameIgnoreCase(specialityDTO.getName())){
             throw new UserAlreadyExistsException("This Speciality Already Registered!");
         }

        Speciality speciality = new Speciality();
        speciality.setName(specialityDTO.getName().toUpperCase());

        return specialityMapper.toDTO(specialityRepository.save(speciality));
    }

    @Override
    public List<SpecialityDTO> findAll() {
        List<Speciality> specialities = specialityRepository.findAll();

        if(specialities.isEmpty()){
            throw new NotFoundException("No Specialities Found");
        }
       return specialityMapper.specialityDTOList(specialities);
    }

    @Override
    public void deleteSpecialityById(Integer id) {

        specialityRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No Speciality Found For ID: " + id));
        specialityRepository.deleteById(id);
    }

    @Override
    public SpecialityDetailsDTOPage getSpecialityDetailsInfo(Integer specialityId, int page, int size) {
         Page<SpecialityDetailsInfo> specialityDetailsInfoList =
                 clinicRepository.getSpecialityDetailsInfo(specialityId, PageRequest.of(page, size));


         if(!specialityDetailsInfoList.hasContent()){
             throw new NotFoundException("No Details Found");
         }

        return specialityDetailsMapper.toSpecialityDetailsDTOPage(specialityDetailsInfoList);
    }

}
