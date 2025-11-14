package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.UserDTO;
import clinicmangement.com.WeCare.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserDTO toDTO(User user){

        if(user == null){
            throw new NullPointerException("User is Null");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMobile(user.getMobile());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

   public User toEntity(UserDTO userDTO){
        if(userDTO == null){
            throw new NullPointerException("UserDTO is null");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobile(userDTO.getMobile());
        user.setEmail(userDTO.getEmail());

        return user;
   }
}
