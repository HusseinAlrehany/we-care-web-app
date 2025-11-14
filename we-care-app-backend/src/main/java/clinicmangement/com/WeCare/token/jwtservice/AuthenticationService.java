package clinicmangement.com.WeCare.token.jwtservice;

import clinicmangement.com.WeCare.DTO.DoctorSignupRequest;
import clinicmangement.com.WeCare.DTO.SignInRequest;
import clinicmangement.com.WeCare.DTO.PatientSignupRequest;
import clinicmangement.com.WeCare.DTO.UserDTO;
import clinicmangement.com.WeCare.apiresponse.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    boolean hasUserWithEmail(String email);

    UserDTO signUpAsPatient(PatientSignupRequest patientSignupRequest);

    UserDTO signUpAsDoctor(DoctorSignupRequest doctorSignupRequest) throws IOException;

    AuthenticationResponse signIn(SignInRequest signInRequest, HttpServletResponse httpServletResponse);


}
