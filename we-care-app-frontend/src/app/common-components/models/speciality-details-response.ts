import { ClinicDTO } from "./clinic-dto";
import { ShortDoctorsDTO } from "./short-doctors-dto";

export interface SpecialityDetailsResponse {

    message: string;
    payload: {
        shortDoctorDTOS: ShortDoctorsDTO[],
        clinicDTOS: ClinicDTO [],
        totalPages: number,
        totalElements: number,
        size: number,
        number: number,
        numberOfElements: number

    };
}
