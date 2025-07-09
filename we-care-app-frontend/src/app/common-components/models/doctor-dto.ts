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
    fees: number,
    clinicStates: string[] ;
    clinicCities: string[];
}
