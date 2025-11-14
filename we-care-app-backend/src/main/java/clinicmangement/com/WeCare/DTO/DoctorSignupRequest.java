package clinicmangement.com.WeCare.DTO;
import clinicmangement.com.WeCare.validation.imgcustomvalidation.ImgValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class DoctorSignupRequest {
    //doctor and patient shared fields
    //to be updated to show group validation message for the user in UI
    private Integer id;

    @NotBlank(message = "firstName is Required")
    @Pattern(regexp = "^[\\p{L}]+$", message = "Invalid First Name")//regex to validate any language
    private String firstName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Password must be at least 8 characters long, " +
                      "contain at least one uppercase letter, " +
                      "one number, and one special character" )
    private String password;

    @NotBlank(message = "mobile is required")
    @Pattern( regexp =  "^(010|011|012|015)\\d{8}$", message = "Invalid Mobile Number")
    private String mobile;


    //DOCTOR SPECIFIC FIELDS
    @NotBlank(message = "last name is required")
    @Pattern(regexp = "^[\\p{L}]+$", message = "Invalid last name")
    private String lastName;

    @NotBlank(message = "brief introduction is required")
    @Size(max = 500, message = "introduction should not exceed 500 letters")
    private String briefIntroduction;

    private LocalDateTime joiningDate;
    private Integer specialityId;
    //private String specialityName;

    private byte[] medicalCardByte;

    @ImgValidation
    private MultipartFile medicalCardFile;
    private byte[] doctorPhotoByte;

    private Integer fees;

    @ImgValidation
    private MultipartFile doctorPhotoFile;

    private String notificationToken;
}


//to avoid jackson deserialization issue
//used @JsonProperty since lombok will generate
//setter and getters for that property like this void setDoctor(boolean doctor)
//so jackson expects json property to be doctor not isDoctor
//so we have two options using @JsonProperty("isDoctor")
//or changing the property name from isDoctor to doctor
// @JsonProperty("isDoctor")
//private boolean isDoctor;
