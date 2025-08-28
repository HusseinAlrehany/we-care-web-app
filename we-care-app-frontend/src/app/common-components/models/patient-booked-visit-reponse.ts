import { PatientBookedVisitsProjection } from "./patient-booked-visits-projection";

export interface PatientBookedVisitReponse {

    message: string,
    payload : PatientBookedVisitsProjection [];
}
