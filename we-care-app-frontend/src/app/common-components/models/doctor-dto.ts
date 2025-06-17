export interface DoctorDTO {
    id?: number,
    firstName: string,
    lastName: string,
    briefIntroduction: string,
    joiningDate: string,
    doctorImageURL?: string,
    totalRating: number,
    averageRating: number,
    specialityName: string,
    clinicStates: string[] ;
    clinicCities: string[];
}
