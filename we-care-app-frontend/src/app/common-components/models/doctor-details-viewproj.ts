import { ClinicDetailsViewProj } from "./clinic-details-view-proj";

export interface DoctorDetailsViewproj {
     
      doctorId: number,
      firstName: string,
      lastName: string,
      specialityName: string,
      briefIntroduction: string,
      fees?: number,
      doctorImageURL: string,
      docClinics: ClinicDetailsViewProj[]


}
