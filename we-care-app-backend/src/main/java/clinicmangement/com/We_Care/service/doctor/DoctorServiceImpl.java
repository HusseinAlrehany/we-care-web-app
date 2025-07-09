package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.DTO.ClinicDTOProjection;
import clinicmangement.com.We_Care.DTO.SameDoctorsPage;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.DoctorMapper;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.repository.clinic.ClinicRepository;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final ClinicRepository clinicRepository;

    @Override
    public byte[] getMedicalCardById(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Doctor Not Found With ID: " + id));

        return doctor.getMedicalCard();
    }

    @Override
    public byte[] getDoctorPhotoById(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Doctor Not Found With ID: " + id));
        return doctor.getDoctorPhoto();
    }

    @Override
    public SameDoctorsPage findAllSameSpecialityDocs(Integer userId, int pageNumber, int pageSize) {

        Page<Doctor> doctorPage = doctorRepository.findAllSameSpecialityDocs(userId, PageRequest.of(pageNumber, pageSize));

        if(!doctorPage.hasContent()){
            throw new NotFoundException("No Doctor Found With ID: " + userId);
        }

        return doctorMapper.toSameDoctorsPage(doctorPage);
    }

    @Override
    public List<ClinicDTOProjection> getAllMyClinicsByUserId(Integer userId) {
         List<ClinicDTOProjection> clinicDTOProjections =
                 clinicRepository.getAllClinicByUserId(userId);
         if(clinicDTOProjections.isEmpty()){
             throw new NotFoundException("No Clinics Registered Yet userId " + userId);
         }
        return clinicDTOProjections;
    }

}
