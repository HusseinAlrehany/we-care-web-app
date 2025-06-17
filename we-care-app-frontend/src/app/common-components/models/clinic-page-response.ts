import { ClinicDTO } from "./clinic-dto"
import { ClinicDTOPage } from "./clinic-dtopage"

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
