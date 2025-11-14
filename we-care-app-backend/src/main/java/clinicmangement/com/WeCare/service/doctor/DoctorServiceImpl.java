package clinicmangement.com.WeCare.service.doctor;

import clinicmangement.com.WeCare.DTO.*;
import clinicmangement.com.WeCare.enums.VisitStatus;
import clinicmangement.com.WeCare.exceptions.types.InvalidUserNameOrPasswordException;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.mapper.DoctorMapper;
import clinicmangement.com.WeCare.mapper.DoctorPreviousVisitsMapper;
import clinicmangement.com.WeCare.models.*;
import clinicmangement.com.WeCare.repository.city.CityRepository;
import clinicmangement.com.WeCare.repository.clinic.ClinicRepository;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.states.StateRepository;
import clinicmangement.com.WeCare.repository.user.UserRepository;
import clinicmangement.com.WeCare.repository.visit.VisitBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final ClinicRepository clinicRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final VisitBookingRepository visitBookingRepository;
    private final PasswordEncoder encoder;
    private final DoctorPreviousVisitsMapper doctorPreviousVisitsMapper;

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

    @Override
    public void addClinic(Integer userId, DocClinicDTO docClinicDTO) {

        Doctor doctor = doctorRepository.findDoctorByUserId(userId);

        States state = stateRepository.findById(docClinicDTO.getStateId())
                .orElseThrow(()-> new NotFoundException("No State Found for ID:" + docClinicDTO.getStateId()));
        Cities city = cityRepository.findById(docClinicDTO.getCityId())
                .orElseThrow(()-> new NotFoundException("No City Found For ID:" + docClinicDTO.getCityId()));

        Clinic clinic = new Clinic();
        clinic.setDoctor(doctor);
        clinic.setCity(city);
        clinic.setState(state);
        clinic.setClinicMobile(docClinicDTO.getClinicMobile());
        clinic.setAddress(docClinicDTO.getAddress());
        clinic.setClinicLocation("Not Activated");

        clinicRepository.save(clinic);
    }

    @Override
    public UserProfileDTOProjection getUserProfile(Integer userId) {
        UserProfileDTOProjection userProfileDTOProjection =
        userRepository.findProfileById(userId);

        if(userProfileDTOProjection == null){
            throw new NotFoundException("No Profile Found ID: " + userId);
        }
        return userProfileDTOProjection;
    }

    @Override
    public void updateUserProfile(Integer userId, UserProfileUpdateRequest userProfileUpdateRequest) {

        Doctor doctor = doctorRepository.findDoctorByUserId(userId);

        if(doctor == null){
            throw new NotFoundException("No Doctor Found with UserId: " + userId);
        }

        doctorMapper.updateDoctorAndUserProfileFromDTO(doctor, userProfileUpdateRequest);

        doctorRepository.save(doctor);
        //no need to save the user entity too
        //because Doctor is the owner of the relation(the side contains the foreign key column and JoinColumn annotation)
        //so the associated entity (user) is already loaded and managed in the persistence context
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, User user) {

        if(encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            user.setPassword(encoder.encode(
                    changePasswordRequest.getNewPassword()
            ));

            userRepository.save(user);
        }else {
            throw new InvalidUserNameOrPasswordException("Wrong Current Password");
        }

    }

    @Override
    public DoctorPreviousVisitsPage getDoctorPreviousVisits(Integer id,
                                                            Integer pageNumber,
                                                            Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<DoctorsVisitsDTOProjection> doctorPreviousVisitsDTOProjectionPage = visitBookingRepository.getDoctorPreviousVisits(id, VisitStatus.CHECKED.name(), pageable);

        if(!doctorPreviousVisitsDTOProjectionPage.hasContent()){
            throw new NotFoundException("No Previous Visits Found");
        }
        return doctorPreviousVisitsMapper.toDoctorPreviousVisitsPage(doctorPreviousVisitsDTOProjectionPage);
    }

    @Override
    public List<DoctorsVisitsDTOProjection> getDoctorTodayVisits(Integer userId, LocalDate today) {

        List<DoctorsVisitsDTOProjection> doctorsVisitsDTOProjections = visitBookingRepository.getDoctorsTodayVisits(
                userId, LocalDate.now());

        if(doctorsVisitsDTOProjections.isEmpty()){
            throw new NotFoundException("No Visits Found For Today!");
        }

        return doctorsVisitsDTOProjections;
    }

}
