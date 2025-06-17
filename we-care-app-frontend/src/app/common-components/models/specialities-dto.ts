import { DoctorDTO } from "./doctor-dto";

export interface SpecialitiesDTO {

   id?: number,
   name: string,
   doctors: DoctorDTO[] | null;
   numberOfDoctors: number;
    
}
