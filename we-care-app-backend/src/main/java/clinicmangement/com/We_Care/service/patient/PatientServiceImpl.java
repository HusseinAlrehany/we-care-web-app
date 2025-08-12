package clinicmangement.com.We_Care.service.patient;

import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTO;
import clinicmangement.com.We_Care.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.mapper.DoctorClinicScheduleDTOMapper;
import clinicmangement.com.We_Care.mapper.ScheduleMapper;
import clinicmangement.com.We_Care.models.Clinic;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.ScheduleAppointment;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.search.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final DoctorRepository doctorRepository;
    private final DoctorClinicScheduleDTOMapper doctorClinicScheduleDTOMapper;

    //getting doctor with one clinic with all related schedule of that clinic
    //if a doctor has two clinic, this doctor will show twice with each clinic
    @Override
    public DoctorClinicScheduleDTOPage getDoctorPage(Pageable pageable) {
        List <Doctor> doctors = doctorRepository.findAll();

        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found");
        }

        return doctorClinicScheduleDTOMapper.buildDoctorClinicScheduleDTOPage(doctors, pageable);
    }

    @Override
    public DoctorClinicScheduleDTOPage filterDoctorClinicSchedule(Pageable pageable,
                                                                  String doctorName,
                                                                  String specialityName,
                                                                  String stateName,
                                                                  String cityName) {

        Specification<Doctor> doctorSpecification = Specification
                                                  .where(DoctorSpecification.hasName(doctorName))
                .and(DoctorSpecification.hasSpeciality(specialityName))
                .and(DoctorSpecification.hasState(stateName))
                .and(DoctorSpecification.hasCity(cityName));

        List<Doctor> doctors = doctorRepository.findAll(doctorSpecification);

        if(doctors.isEmpty()){
            throw new NotFoundException("No Doctors Found For that Criteria");
        }

        //passing stateName, cityName to call overloaded method to filter stateName, cityName
        return doctorClinicScheduleDTOMapper.buildDoctorClinicScheduleDTOPage(doctors, pageable, stateName, cityName);
    }

}
