import { ClinicDTO } from "./clinic-dto";
import { ShortDoctorsDTO } from "./short-doctors-dto";
import { SpecialityDetailsInfo } from "./speciality-details-info";

export interface SpecialityDetailsResponse {

    message: string;
    payload: {
        specialityDetailsInfoList: SpecialityDetailsInfo[];
        totalPages: number;
        totalElements: number;
        size: number;
        number: number;
        numberOfElements: number;
    }
}
