package clinicmangement.com.We_Care.mapper;

import clinicmangement.com.We_Care.DTO.VisitBookingDTO;
import clinicmangement.com.We_Care.enums.VisitStatus;
import clinicmangement.com.We_Care.models.VisitBooking;
import org.springframework.stereotype.Component;

@Component
public class VisitBookingMapper {



    public VisitBooking toEntity(VisitBookingDTO visitBookingDTO){

        if(visitBookingDTO == null){
            throw new NullPointerException("DTO is NULL");
        }

        VisitBooking visitBooking = new VisitBooking();
        visitBooking.setVisitStatus(VisitStatus.BOOKED);
        visitBooking.setPatientMobile(visitBookingDTO.getPatientMobile());
        visitBooking.setPatientName(visitBookingDTO.getPatientName());


        return visitBooking;
    }

    public VisitBookingDTO toDTO(VisitBooking visitBooking){

        if(visitBooking == null){
            throw new NullPointerException("visitBooking is NULL");
        }

        VisitBookingDTO visitBookingDTO = new VisitBookingDTO();
        visitBookingDTO.setId(visitBooking.getId());
        visitBookingDTO.setVisitStatus(visitBooking.getVisitStatus());
        visitBookingDTO.setPatientName(visitBooking.getPatientName());
        visitBookingDTO.setPatientMobile(visitBooking.getPatientMobile());
        visitBookingDTO.setClinicId(visitBooking.getClinic().getId());
        visitBookingDTO.setDoctorId(visitBooking.getDoctor().getId());
        visitBookingDTO.setScheduleId(visitBooking.getSchedule().getId());
        visitBookingDTO.setUserId(visitBooking.getUser().getId());

        return visitBookingDTO;
    }
}
