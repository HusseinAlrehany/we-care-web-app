import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpecialitiesDTO } from '../../../common-components/models/specialities-dto';
import { AddSpecialityResponse } from '../../../common-components/models/add-speciality-response';
import { SpecialitiesResponse } from '../../../common-components/models/specialities-response';
import { DoctorsResponse } from '../../../common-components/models/doctors-response';
import { DoctorSignupResponse } from '../../../common-components/models/doctor-signup-response';
import { ClinicsResponse } from '../../../common-components/models/clinics-response';
import { ClinicDTO } from '../../../common-components/models/clinic-dto';
import { ClinicPageResponse } from '../../../common-components/models/clinic-page-response';
import { ShortDoctorsResponse } from '../../../common-components/models/short-doctors-response';
import { SpecialityDetailsResponse } from '../../../common-components/models/speciality-details-response';

const baseURL = "http://localhost:8080/we-care/";

@Injectable({
  providedIn: 'root'
})
export class AdminService{

  constructor(private httpClient: HttpClient) { }

  addSpeciality(specialityDTO: SpecialitiesDTO): Observable<AddSpecialityResponse> {

    return this.httpClient.post<AddSpecialityResponse>(baseURL + `add-speciality`, specialityDTO, {
      withCredentials: true
    });
  }

  getAllSpecialities(): Observable<SpecialitiesResponse> {
    return this.httpClient.get<SpecialitiesResponse>(baseURL + `specialities`,{
      withCredentials: true
    });
  }

  deleteSpecialityById(id: number): Observable<any> {
    return this.httpClient.delete(baseURL + `speciality/${id}`, {
      withCredentials: true
    });
  }

  getSpecialityDetailsById(specialityId: number, page: number, size: number): Observable<SpecialityDetailsResponse>{
    return this.httpClient.get<SpecialityDetailsResponse>(baseURL + `speciality-details/${specialityId}?page=${page - 1}&&size=${size}`, {
      withCredentials: true,
    });
  }

  getAllDoctors(): Observable<DoctorsResponse> {
    return this.httpClient.get<DoctorsResponse>(baseURL + `doctors`, {
      withCredentials: true
    });
  }

  addDoctor(doctorSignUpRequest: FormData): Observable<DoctorSignupResponse> {

    return this.httpClient.post<DoctorSignupResponse>(baseURL + `add-doctor`, doctorSignUpRequest, {
      withCredentials: true,
    });
  }

  deleteDoctor(id: number): Observable<any> {
    return this.httpClient.delete(baseURL + `doctor/${id}`,  {
      withCredentials: true
    });
  }

  addClinic(clinicDTO: ClinicDTO): Observable<ClinicsResponse>{
    return this.httpClient.post<ClinicsResponse>(baseURL + `add-clinic`, clinicDTO, {
      withCredentials: true
    });
  }

  //not used
  getAllClinics(): Observable<ClinicsResponse> {

    return this.httpClient.get<ClinicsResponse>(baseURL + `clinics`, {
      withCredentials: true,
    });
  }

  getClinicPage(page: number, size: number): Observable<ClinicPageResponse> {
    return this.httpClient.get<ClinicPageResponse>(baseURL + `clinics-page?page=${page - 1}&size=${size}`, {
      withCredentials: true,
    });
  }

  getAllShortDoctors(): Observable<ShortDoctorsResponse> {
    return this.httpClient.get<ShortDoctorsResponse>(baseURL + `all-short-doctor`, {
      withCredentials: true,
    });
  }





  

}
