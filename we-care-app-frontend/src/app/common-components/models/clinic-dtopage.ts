import { ClinicDTO } from "./clinic-dto";

export interface ClinicDTOPage {

    content: ClinicDTO [];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    numberOfElements: number

}
