import { ClinicDTO } from "./clinic-dto";

export interface ClinicsResponse {
    message: string,
    payload: ClinicDTO [];
}
