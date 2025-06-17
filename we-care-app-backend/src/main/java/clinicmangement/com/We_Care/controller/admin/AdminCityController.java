package clinicmangement.com.We_Care.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/we-care")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCityController {


}
