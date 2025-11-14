package clinicmangement.com.WeCare.service.admin;

import clinicmangement.com.WeCare.DTO.SpecialityDTO;
import clinicmangement.com.WeCare.DTO.SpecialityDetailsDTOPage;
import clinicmangement.com.WeCare.DTO.SpecialityDetailsInfoProjection;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.exceptions.types.UserAlreadyExistsException;
import clinicmangement.com.WeCare.mapper.ShortDoctorDTOMapper;
import clinicmangement.com.WeCare.mapper.SpecialityDetailsMapper;
import clinicmangement.com.WeCare.mapper.SpecialityMapper;
import clinicmangement.com.WeCare.models.Speciality;
import clinicmangement.com.WeCare.repository.clinic.ClinicRepository;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.speciality.SpecialityRepository;
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
         Page<SpecialityDetailsInfoProjection> specialityDetailsInfoList =
                 clinicRepository.getSpecialityDetailsInfo(specialityId, PageRequest.of(page, size));


         if(!specialityDetailsInfoList.hasContent()){
             throw new NotFoundException("No Details Found");
         }

        return specialityDetailsMapper.toSpecialityDetailsDTOPage(specialityDetailsInfoList);
    }

}
