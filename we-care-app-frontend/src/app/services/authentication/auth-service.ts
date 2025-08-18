import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SignInRequest } from '../../common-components/models/sign-in-request';
import { Observable } from 'rxjs';
import { StoredUser } from '../../common-components/models/stored-user';
import { PatientSignupRequest } from '../../common-components/models/patient-signup-request';
import { PatientSignupResponse } from '../../common-components/models/patient-signup-response';
import { SpecialitiesResponse } from '../../common-components/models/specialities-response';
import { DoctorSignupResponse } from '../../common-components/models/doctor-signup-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseURL = "http://localhost:8080/";

  constructor(private httpClient: HttpClient) { }

signIn(signInRequest: SignInRequest): Observable<StoredUser> {

  return this.httpClient.post<StoredUser>(this.baseURL + `signin`, signInRequest, {
    withCredentials: true
  })
}

signupAsPatient(patientSignupRequest: PatientSignupRequest): Observable<PatientSignupResponse> {
    return this.httpClient.post<PatientSignupResponse>(this.baseURL + `signup/patient`, patientSignupRequest);
}

signupAsDoctor(doctorSignupRequest: FormData): Observable<DoctorSignupResponse>{
   return this.httpClient.post<DoctorSignupResponse>(this.baseURL + `signup/doctor`, doctorSignupRequest);
}

logout(): Observable<StoredUser>{

  return this.httpClient.post<StoredUser>(this.baseURL + `logout`, null, {
    withCredentials: true
  })
}

getAllSpecialities(): Observable<SpecialitiesResponse> {
  return this.httpClient.get<SpecialitiesResponse>(this.baseURL + `specialities`);
}


}
