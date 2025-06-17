package clinicmangement.com.We_Care.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;

}
