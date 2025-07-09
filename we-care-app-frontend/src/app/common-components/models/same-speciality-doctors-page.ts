import { SameSpecialityDoctorsDTO } from "./same-speciality-doctors-dto";

export interface SameSpecialityDoctorsPage {
        content: SameSpecialityDoctorsDTO [];
        totalPages: number;
        totalElements: number;
        pageSize: number;
        pageNumber: number;
        numberOfElements: number
}
