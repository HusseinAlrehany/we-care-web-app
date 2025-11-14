package clinicmangement.com.WeCare.mapper;

import clinicmangement.com.WeCare.DTO.DoctorClinicScheduleDTO;
import clinicmangement.com.WeCare.DTO.DoctorClinicScheduleDTOPage;
import clinicmangement.com.WeCare.DTO.ScheduleDTORead;
import clinicmangement.com.WeCare.enums.ScheduleStatus;
import clinicmangement.com.WeCare.models.Clinic;
import clinicmangement.com.WeCare.models.Doctor;
import clinicmangement.com.WeCare.models.ScheduleAppointment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorClinicScheduleDTOMapper {

    private final ScheduleMapper scheduleMapper;

    public DoctorClinicScheduleDTO toDoctorClinicScheduleDTO(Doctor doctor,
                                                             Clinic clinic,
                                                             List<ScheduleAppointment> appointmentList){

        List<ScheduleDTORead> scheduleDTOReads =
                appointmentList.stream()
                        .map(scheduleMapper::toScheduleDTORead)
                        .toList();

        DoctorClinicScheduleDTO dto = new DoctorClinicScheduleDTO();
        dto.setDoctorId(doctor.getId());
        dto.setUserId(doctor.getUser().getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setAverageRating(doctor.getAverageRating());
        dto.setTotalRating(doctor.getTotalRating());
        dto.setBriefIntroduction(doctor.getBriefIntroduction());
        dto.setFees(doctor.getFees());
        dto.setJoiningDate(doctor.getJoiningDate());


        if(doctor.getLastUpdated() != null){
            long timeStamp = doctor.getLastUpdated().toInstant(ZoneOffset.UTC).toEpochMilli();
            dto.setLastUpdated(timeStamp);
            //setting the image URL with URL versioning with timestamp
            dto.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo?ts=" + timeStamp);
        }else {
            dto.setLastUpdated(null);
            dto.setDoctorImageURL("/doctors/" + doctor.getId() + "/photo");
        }

        dto.setSpecialityId(doctor.getSpeciality().getId());
        dto.setSpecialityName(doctor.getSpeciality().getName());

        dto.setClinicId(clinic.getId());
        dto.setAddress(clinic.getAddress());
        dto.setClinicMobile(clinic.getClinicMobile());
        dto.setClinicState(clinic.getState().getStateName().toString());
        dto.setClinicCity(clinic.getCity() != null ? clinic.getCity().getCityName() : null);

        dto.setScheduleDTOs(scheduleDTOReads);
        dto.setScheduleStatus(ScheduleStatus.ACTIVE);

       return dto;
    }

    public DoctorClinicScheduleDTOPage toDoctorClinicScheduleDTOPage(List<DoctorClinicScheduleDTO> content,
                                                                     Pageable pageable,
                                                                     Long totalElements){
        DoctorClinicScheduleDTOPage doctorClinicScheduleDTOPage = new DoctorClinicScheduleDTOPage();
        doctorClinicScheduleDTOPage.setContent(content);
        doctorClinicScheduleDTOPage.setSize(pageable.getPageSize());
        doctorClinicScheduleDTOPage.setNumber(pageable.getPageNumber());
        doctorClinicScheduleDTOPage.setNumberOfElements(content.size());
        doctorClinicScheduleDTOPage.setTotalElements(totalElements);
        doctorClinicScheduleDTOPage.setTotalPages((int)Math.ceil((double)totalElements / pageable.getPageSize()));

        return doctorClinicScheduleDTOPage;

    }


    //this method mapper for building doctor clinic schedule when a criteria for doctorName|specialityName|stateName|cityName selected
    //So if user select a state and city, only the specified doctor which has clinic in the selected state and city will show
    //even if that doctor has more than one clinic
    public DoctorClinicScheduleDTOPage buildDoctorClinicScheduleDTOPage(List<Doctor> doctors,
                                                                        Pageable pageable,
                                                                        @Nullable String stateName,
                                                                        @Nullable String cityName){

        List<DoctorClinicScheduleDTO> mappedResult = new ArrayList<>();

        //loop through every doctor in doctors list
        for(Doctor doctor: doctors){
            //loop over every clinic and extract all clinics for every doctor
            for(Clinic clinic: doctor.getClinicList()){
                 //optional filter by stateName
                 if(stateName != null && (clinic.getState() == null
                                || !stateName.trim().equalsIgnoreCase(clinic.getState().getStateName().toString()))){

                     continue;
                 }

                 //optional filter by cityName
                if(cityName != null && (clinic.getCity() == null
                                || !cityName.trim().equalsIgnoreCase(clinic.getCity().getCityName()))){
                    continue;
                }


                //loop through every doctors's schedule list
                //extract every schedule list according to current clinic ID, and assign it to schedules
                List<ScheduleAppointment> schedules = doctor.getDoctorScheduleList()
                        .stream()
                        //filter by showing the current selected clinic schedule and active schedule
                        .filter(s-> s.getClinic().getId().equals(clinic.getId())
                                && s.getScheduleStatus().equals(ScheduleStatus.ACTIVE))
                        .toList();

                DoctorClinicScheduleDTO doctorClinicScheduleDTO =
                        toDoctorClinicScheduleDTO(doctor, clinic, schedules);

                mappedResult.add(doctorClinicScheduleDTO);
            }
        }

       // int start = (int)pageable.getOffset();
       // int end = Math.min(start + pageable.getPageSize(), mappedResult.size());
        int total = mappedResult.size();
        int fromIndex = pageable.getPageNumber() * pageable.getPageSize();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), total);

        if(fromIndex >= total){
            return toDoctorClinicScheduleDTOPage(Collections.emptyList(), pageable, (long)total);
        }

        List<DoctorClinicScheduleDTO> pagedContent = mappedResult.subList(fromIndex, toIndex);


        return toDoctorClinicScheduleDTOPage(
                pagedContent,
                pageable,
                (long)mappedResult.size());

    }

    //this method overloading
    //this method mapper for building doctor clinic schedule page without filtering (get all doctor clinic schedule)
    //it will get every doctor with it's related clinics and it's related schedules
    //and if a doctor has more than one clinic it will show many times but with it's related clinics and schedules
    public DoctorClinicScheduleDTOPage buildDoctorClinicScheduleDTOPage(List<Doctor> doctors,
                                                                        Pageable pageable){

        return buildDoctorClinicScheduleDTOPage(doctors, pageable,null, null);

    }

    }

