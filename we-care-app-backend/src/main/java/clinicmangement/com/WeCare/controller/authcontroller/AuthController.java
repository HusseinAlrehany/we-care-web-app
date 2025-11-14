package clinicmangement.com.WeCare.controller.authcontroller;

import clinicmangement.com.WeCare.DTO.DoctorSignupRequest;
import clinicmangement.com.WeCare.DTO.PatientSignupRequest;
import clinicmangement.com.WeCare.DTO.SignInRequest;
import clinicmangement.com.WeCare.DTO.UserDTO;
import clinicmangement.com.WeCare.apiresponse.ApiResponse;
import clinicmangement.com.WeCare.apiresponse.AuthenticationResponse;
import clinicmangement.com.WeCare.token.jwtservice.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/patient")
    public ResponseEntity<ApiResponse<UserDTO>> signupAsPatient(@RequestBody @Valid PatientSignupRequest patientSignupRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Success", authenticationService.signUpAsPatient(patientSignupRequest)));

    }

    @PostMapping(value = "/signup/doctor")
    public ResponseEntity<ApiResponse<UserDTO>> signupAsDoctor(@ModelAttribute @Valid DoctorSignupRequest doctorSignupRequest) throws IOException {
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(new ApiResponse<>("Account Created Successfully", authenticationService.signUpAsDoctor(doctorSignupRequest)));
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> signin(@RequestBody SignInRequest signInRequest, HttpServletResponse httpServletResponse){


            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>("Sign in Success", authenticationService.signIn(signInRequest, httpServletResponse)));
        }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response){
          try{

              ResponseCookie deletedCookie = ResponseCookie.from("jwt", "")
                      .httpOnly(true)
                      .secure(false)
                      .sameSite("strict")
                      .path("/")
                      .maxAge(0)
                      .build();


              response.addHeader(HttpHeaders.SET_COOKIE, deletedCookie.toString());
              return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Logout Success"));

          }catch(Exception ex){

               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(new ApiResponse<>("Logout Failed!!"));
          }
    }
}
