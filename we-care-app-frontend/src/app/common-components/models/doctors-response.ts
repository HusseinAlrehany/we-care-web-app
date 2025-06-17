import { DoctorDTO } from "./doctor-dto";

export interface DoctorsResponse {
    message: string,
    payload: DoctorDTO [];
}
