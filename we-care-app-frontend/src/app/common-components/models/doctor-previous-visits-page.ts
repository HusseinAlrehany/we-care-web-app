import { DoctorPreviousVisitDtoProjection } from "./doctor-previous-visit-dto-projection";

export interface DoctorPreviousVisitsPage {
    message: string,
    payload: {
        content: DoctorPreviousVisitDtoProjection [],
        totalPages: number;
        totalElements: number;
        size: number;
        number: number;
        numberOfElements: number
}
}