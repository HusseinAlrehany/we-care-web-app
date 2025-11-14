package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.DoctorDTO;
import clinicmangement.com.WeCare.DTO.SpecialityDTO;
import clinicmangement.com.WeCare.models.Speciality;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpecialityMapper {

    private final DoctorMapper doctorMapper;


    public SpecialityDTO toDTO(Speciality speciality){
        if(speciality == null){
            throw new NullPointerException("Speciality is null");
        }

        SpecialityDTO specialityDTO = new SpecialityDTO();
        specialityDTO.setId(speciality.getId());
        specialityDTO.setName(speciality.getName());

        if(speciality.getDoctors() != null){
           List<DoctorDTO> doctorDTOList = speciality.getDoctors().stream()
                   .map(doctorMapper::toDTO)
                   .toList();

            specialityDTO.setDoctors(doctorDTOList);
            specialityDTO.setNumberOfDoctors(doctorDTOList.size());
        }

        return specialityDTO;
    }

    //sorted list of specialityDTO according to natural order (alphapitical order)
    public List<SpecialityDTO> specialityDTOList(List<Speciality> specialities){

        return specialities != null? specialities.stream().map(this::toDTO)
                .sorted(Comparator.comparing(SpecialityDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList()) : new ArrayList<>();
    }


}
