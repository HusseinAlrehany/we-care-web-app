export interface UserProfileUpdateRequest {

    password: string,
    briefIntroduction: string,
    fees: number,
    medicalCardFile: File,
    doctorImgFile: File
}
