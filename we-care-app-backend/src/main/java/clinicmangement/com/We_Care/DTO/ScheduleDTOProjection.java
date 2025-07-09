package clinicmangement.com.We_Care.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
public class ScheduleDTOProjection {


    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String clinicAddress;


    }

