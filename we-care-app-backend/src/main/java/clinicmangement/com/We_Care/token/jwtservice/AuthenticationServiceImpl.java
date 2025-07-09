package clinicmangement.com.We_Care.token.jwtservice;

import clinicmangement.com.We_Care.DTO.DoctorSignupRequest;
import clinicmangement.com.We_Care.DTO.SignInRequest;
import clinicmangement.com.We_Care.DTO.PatientSignupRequest;
import clinicmangement.com.We_Care.DTO.UserDTO;
import clinicmangement.com.We_Care.apiresponse.AuthenticationResponse;
import clinicmangement.com.We_Care.enums.UserRole;
import clinicmangement.com.We_Care.exceptions.types.InvalidUserNameOrPasswordException;
import clinicmangement.com.We_Care.exceptions.types.NotFoundException;
import clinicmangement.com.We_Care.exceptions.types.UserAlreadyExistsException;
import clinicmangement.com.We_Care.mapper.UserMapper;
import clinicmangement.com.We_Care.models.Doctor;
import clinicmangement.com.We_Care.models.Speciality;
import clinicmangement.com.We_Care.models.User;
import clinicmangement.com.We_Care.repository.doctor.DoctorRepository;
import clinicmangement.com.We_Care.repository.speciality.SpecialityRepository;
import clinicmangement.com.We_Care.repository.user.UserRepository;
import clinicmangement.com.We_Care.token.utils.JwtUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements  AuthenticationService{

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final WecareUserDetailsService wecareUserDetailsService;
    private final UserMapper userMapper;


    //creation of an admin user
    @PostConstruct
    public void createAdmin(){

        User adminUser = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminUser == null){
             User admin = new User();
             admin.setFirstName("admin");
             admin.setLastName("Jessy");
             admin.setEmail("admin@we-care.com");
             admin.setMobile("01045387562");
             admin.setUserRole(UserRole.ADMIN);
             admin.setPassword(new BCryptPasswordEncoder().encode("test123"));

            userRepository.save(admin);
        }
    }

    @Override
    public UserDTO signUpAsPatient(PatientSignupRequest patientSignupRequest) {

        if(hasUserWithEmail(patientSignupRequest.getEmail())){
            throw new UserAlreadyExistsException("User already exists with this email");
        }

            User user = new User();
            user.setUserRole(UserRole.PATIENT);
            user.setFirstName(patientSignupRequest.getFirstName());
            user.setLastName(patientSignupRequest.getLastName());
            user.setEmail(patientSignupRequest.getEmail());
            user.setMobile(patientSignupRequest.getMobile());
            user.setPassword(new BCryptPasswordEncoder().encode(patientSignupRequest.getPassword()));

          User  dbUser = userRepository.save(user);

        return userMapper.toDTO(dbUser);
    }

    @Transactional
    @Override
    public UserDTO signUpAsDoctor(DoctorSignupRequest doctorSignupRequest) throws IOException {

        if(hasUserWithEmail(doctorSignupRequest.getEmail())){
            throw new UserAlreadyExistsException("Doctor already registered with this email");
        }

        User user = new User();
        user.setUserRole(UserRole.DOCTOR);
        user.setFirstName(doctorSignupRequest.getFirstName());
        user.setLastName(doctorSignupRequest.getLastName());
        user.setMobile(doctorSignupRequest.getMobile());
        user.setEmail(doctorSignupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(doctorSignupRequest.getPassword()));

        User  dbUser = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorSignupRequest.getFirstName());
        doctor.setLastName(doctorSignupRequest.getLastName());
        doctor.setFees(doctorSignupRequest.getFees());

        Optional<Speciality> speciality = specialityRepository.findById(doctorSignupRequest.getSpecialityId());
        if(speciality.isEmpty()){
            throw new NotFoundException("No Speciality Found");
        }

        doctor.setSpeciality(speciality.get());

        doctor.setUser(user);
        doctor.setBriefIntroduction(doctorSignupRequest.getBriefIntroduction());
        doctor.setJoiningDate(LocalDateTime.now());
        doctor.setDoctorPhoto(doctorSignupRequest.getDoctorPhotoFile().getBytes());
        doctor.setMedicalCard(doctorSignupRequest.getMedicalCardFile().getBytes());

        doctorRepository.save(doctor);

        return userMapper.toDTO(dbUser);
    }


    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest,
                                         HttpServletResponse httpServletResponse) {
          //check if this user is authenticated(have email, password in database)
        try{
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                     signInRequest.getEmail(),
                     signInRequest.getPassword()
             ));
        }catch(BadCredentialsException bce){
            throw new InvalidUserNameOrPasswordException("Invalid username or password");
        }

        final UserDetails  userDetails = wecareUserDetailsService.loadUserByUsername(
                signInRequest.getEmail());
        final String jwtToken = jwtUtils.generateToken(userDetails);

        Optional<User> optionalUser = userRepository.findFirstByEmail(signInRequest.getEmail());
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("No User Found");
        }

        //store the jwt in http only cookie
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("strict")
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwtToken(jwtToken);
        response.setUserId(optionalUser.get().getId());
        response.setUserRole(optionalUser.get().getUserRole());

        return response;
    }


    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
