package clinicmangement.com.We_Care.service.doctor;

import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

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
}
