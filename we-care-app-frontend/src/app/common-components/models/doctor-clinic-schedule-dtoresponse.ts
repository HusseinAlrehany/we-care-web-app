import { DoctorClinicScheduleDTO } from "./doctor-clinic-schedule-dto"

export interface DoctorClinicScheduleDTOResponse {

    message: string,
    payload: {
        content: DoctorClinicScheduleDTO [],
        totalPages: number,
        totalElements: number,
        size: number,
        number:number,
        numberOfElements: number
    }
}
