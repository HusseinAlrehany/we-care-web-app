package clinicmangement.com.We_Care.service.admin;

import clinicmangement.com.We_Care.DTO.ClinicDTO;
import clinicmangement.com.We_Care.DTO.ClinicDTOPage;
import clinicmangement.com.We_Care.exceptions.types.InvalidInputException;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.ClinicMapper;
import clinicmangement.com.We_Care.models.Cities;
import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.States;
import clinicmangement.com.We_Care.repository.city.CityRepository;
import clinicmangement.com.We_Care.repository.clinic.ClinicRepository;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.states.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminClinicServiceImpl implements AdminClinicService{

    private final ClinicRepository clinicRepository;

    private final StateRepository stateRepository;

    private final CityRepository cityRepository;

    private final DoctorRepository doctorRepository;

    private final ClinicMapper clinicMapper;
    @Override
    public ClinicDTO addClinic(ClinicDTO clinicDTO) {

        States state = stateRepository.findById(clinicDTO.getStateId())
                .orElseThrow(()-> new NotFoundException("No State Found"));
        Cities city = cityRepository.findById(clinicDTO.getCityId())
                .orElseThrow(()-> new NotFoundException("No City Found"));
        Doctor doctor = doctorRepository.findById(clinicDTO.getDoctorId())
                .orElseThrow(()-> new NotFoundException("No Doctor Found"));

        Clinic clinic = clinicMapper.toClinicEntity(clinicDTO);
        clinic.setDoctor(doctor);
        clinic.setState(state);
        clinic.setCity(city);

        Clinic dbClinic = clinicRepository.save(clinic);

        return clinicMapper.toClinicDTO(dbClinic);
    }

    @Override
    public List<ClinicDTO> findAllClinics() {
        List<Clinic> clinics = clinicRepository.findAll();

        if(clinics.isEmpty()){
            throw new NotFoundException("No Clinics Found");
        }
        return clinicMapper.toClinicDTOList(clinics);
    }

    @Override
    public ClinicDTOPage getClinicPage(int page, int size) {
        if(page < 0 || size <= 0){
            throw new InvalidInputException("Invalid page or size");
        }

        Page<Clinic> clinicPage = clinicRepository.findAll(PageRequest.of(page, size));

        if(!clinicPage.hasContent()){
            throw new NotFoundException("No Clinics Found!!");
        }
        return clinicMapper.toClinicDTOPage(clinicPage);
    }

    @Override
    public void deleteClinicById(Integer clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(()-> new NotFoundException("No clinic Found ID:" + clinicId));

        clinicRepository.deleteById(clinicId);
    }

    @Override
    public ClinicDTO findClinicById(Integer id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("No Clinic Found ID:" + id));
        return clinicMapper.toClinicDTO(clinic);
    }

    @Override
    public ClinicDTO updateClinic(ClinicDTO clinicDTO, Integer clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(()-> new NotFoundException("No Clinic Found ID: " + clinicId));

        States state = stateRepository.findById(clinicDTO.getStateId())
                .orElseThrow(()-> new NotFoundException("No State Found"));

        Cities city = cityRepository.findById(clinicDTO.getCityId())
                .orElseThrow(()-> new NotFoundException("No City Found"));

        Doctor doctor = doctorRepository.findById(clinicDTO.getDoctorId())
                .orElseThrow(()-> new NotFoundException("No Doctor Found"));

        clinic.setClinicMobile(clinicDTO.getClinicMobile());
        clinic.setAddress(clinicDTO.getAddress());
        clinic.setState(state);
        clinic.setCity(city);
        clinic.setDoctor(doctor);


        return clinicMapper.toClinicDTO(clinicRepository.save(clinic));
    }
}
