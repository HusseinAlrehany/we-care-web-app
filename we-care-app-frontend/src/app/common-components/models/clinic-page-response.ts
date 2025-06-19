import { ClinicDTO } from "./clinic-dto"

export interface ClinicPageResponse {
    message: string,
    payload: {
        content: ClinicDTO [],
        totalPages: number;
        totalElements: number;
        size: number;
        number: number;
        numberOfElements: number
}
    }
