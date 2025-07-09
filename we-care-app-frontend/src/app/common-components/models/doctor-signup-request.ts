export interface DoctorSignupRequest {

    id?: number,
    firstName: string,
    email: string,
    password: string,
    mobile: string,
    lastName: string,
    briefIntroduction: string,
    specialityId?: number,
    medicalCardFile?: File,
    doctorPhotoFile?: File,
    fees: number
    

}
