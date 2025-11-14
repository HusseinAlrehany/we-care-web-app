package clinicmangement.com.WeCare.token.jwtservice;

import clinicmangement.com.WeCare.DTO.DoctorSignupRequest;
import clinicmangement.com.WeCare.DTO.SignInRequest;
import clinicmangement.com.WeCare.DTO.PatientSignupRequest;
import clinicmangement.com.WeCare.DTO.UserDTO;
import clinicmangement.com.WeCare.apiresponse.AuthenticationResponse;
import clinicmangement.com.WeCare.enums.UserRole;
import clinicmangement.com.WeCare.exceptions.types.InvalidUserNameOrPasswordException;
import clinicmangement.com.WeCare.exceptions.types.NotFoundException;
import clinicmangement.com.WeCare.exceptions.types.UserAlreadyExistsException;
import clinicmangement.com.WeCare.firebase.notificationservice.NotificationService;
import clinicmangement.com.WeCare.mapper.UserMapper;
import clinicmangement.com.WeCare.models.Doctor;
import clinicmangement.com.WeCare.models.Speciality;
import clinicmangement.com.WeCare.models.User;
import clinicmangement.com.WeCare.repository.doctor.DoctorRepository;
import clinicmangement.com.WeCare.repository.speciality.SpecialityRepository;
import clinicmangement.com.WeCare.repository.user.UserRepository;
import clinicmangement.com.WeCare.token.utils.JwtUtils;
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
    private final NotificationService notificationService;

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

    @Transactional //to avoid saving a user row without doctor row if something wrong happens
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

        //save the notification token of the registered doctor
        if(doctorSignupRequest.getNotificationToken() != null){
            System.out.println("INSIDE SIGN UP FUNCTION");
            System.out.println("Notification Token is " + doctorSignupRequest.getNotificationToken());
            notificationService.saveOrUpdateNotificationToken(doctorSignupRequest.getNotificationToken(), user);
        } else {
            System.out.println("Notification Token IS NULL");
        }

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
           //this line added for further check if there is a registered user as doctor
          //but has no doctor row in doctor table (no user_id) in doctor table refer to that user
           User userAsDoctor = userRepository.findByEmailWithDoctor(signInRequest.getEmail())
                   .orElse(null);


        //check if the logged in user(if he is a doctor) is actually a registered doctor
        if(optionalUser.isEmpty() || userAsDoctor == null && optionalUser.get().getUserRole().equals(UserRole.DOCTOR)){
            throw new UsernameNotFoundException("No User or doctor Found with that email");
        }

        //save the notification token used in firebase to send offline notification to the doctor
        if(signInRequest.getNotificationToken() != null && optionalUser.get().getUserRole().equals(UserRole.DOCTOR)){
            System.out.println("Sign in notification Token is " + signInRequest.getNotificationToken());
            notificationService.saveOrUpdateNotificationToken(signInRequest.getNotificationToken(), optionalUser.get());
        }

            //store the jwt in http only cookie
            //because httpOnlyCookie can not be read via (javascript, so it is XSS safe)
            //also it send only with http request to the backend
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
