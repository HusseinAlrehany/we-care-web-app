import { DoctorDTO } from "./doctor-dto"

export interface AddSpecialityResponse {

    message: string,
    payload: {
        id: number,
        name: string,
        doctors: DoctorDTO [] | null
    };
}
