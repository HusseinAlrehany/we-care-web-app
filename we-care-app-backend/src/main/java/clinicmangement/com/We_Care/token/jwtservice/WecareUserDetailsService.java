package clinicmangement.com.We_Care.token.jwtservice;

import clinicmangement.com.We_Care.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WecareUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findFirstByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("No User Found For Email" + username)
        );
    }
}
