import { ScheduleDTO } from "./schedule-dto";

export interface DoctorClinicScheduleDTO {

    doctorId: number,
    userId: number,
    firstName: string,
    lastName: string,
    briefIntroduction: string,
    joiningDate: string,
    doctorImageURL: string,
    totalRating: number | null,
    averageRating: number | null,
    specialityId: number,
    specialityName:string,
    fees: number,
    lastUpdated: string,
    clinicState: string,
    clinicCity: string,
    address: string,
    clinicMobile: string,
    clinicId: number,
    scheduleDTOs: ScheduleDTO [];
}
