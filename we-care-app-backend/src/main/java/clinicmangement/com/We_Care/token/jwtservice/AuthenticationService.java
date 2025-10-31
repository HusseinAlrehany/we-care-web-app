package clinicmangement.com.We_Care.token.jwtservice;

import clinicmangement.com.We_Care.DTO.DoctorSignupRequest;
import clinicmangement.com.We_Care.DTO.SignInRequest;
import clinicmangement.com.We_Care.DTO.PatientSignupRequest;
import clinicmangement.com.We_Care.DTO.UserDTO;
import clinicmangement.com.We_Care.apiresponse.AuthenticationResponse;
import clinicmangement.com.We_Care.apiresponse.AuthenticationResponseWithNotification;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    boolean hasUserWithEmail(String email);

    UserDTO signUpAsPatient(PatientSignupRequest patientSignupRequest);

    UserDTO signUpAsDoctor(DoctorSignupRequest doctorSignupRequest) throws IOException;

    AuthenticationResponse signIn(SignInRequest signInRequest, HttpServletResponse httpServletResponse);


}
