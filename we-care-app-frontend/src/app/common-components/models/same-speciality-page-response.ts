import { SameSpecialityDoctorsDTO } from "./same-speciality-doctors-dto"

export interface SameSpecialityPageResponse {

    message: string,
    payload: {
        content: SameSpecialityDoctorsDTO[],
        totalPages: number;
        totalElements: number;
        pageSize: number;
        pageNumber: number;
        numberOfElements: number
    }
}
