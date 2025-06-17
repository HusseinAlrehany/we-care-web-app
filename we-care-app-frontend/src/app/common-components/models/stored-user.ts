export interface StoredUser {

     message: string;
     payload: {

        userId: string,

        userRole: 'ADMIN' | 'DOCTOR' | 'PATIENT'

     } | null;
     

}
