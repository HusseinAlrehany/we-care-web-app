export interface DoctorSignupResponse {

     message: string,
    payload: {
    id?: number,
    firstName: string,
    lastName: string,
    email: string,
    mobile: string,
    password: string | null
    };
}
