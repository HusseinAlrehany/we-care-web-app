import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { StatesResponse } from './models/states-response';
import { CitiesResponse } from './models/cities-response';
import { DoctorsResponse } from './models/doctors-response';
import { DoctorClinicScheduleDTOResponse } from './models/doctor-clinic-schedule-dtoresponse';
import { SpecialitiesResponse } from './models/specialities-response';
import { ApiResponse } from './api-response/api-response';
import { DoctorFilter } from './models/doctor-filter';
import { BookedDoctorDTO } from './models/booked-doctor-dto';

const baseURL = "http://localhost:8080/";


@Injectable({
  providedIn: 'root'
})
export class SharedService {

  //using doctors list as observable to be used in all doctors component
  //to make angular reinitialize the all doctors component again
  //when the user still on the same router(admin/view_doctors)
  private reloadDoctorsList = new Subject<void>();

  reloadDoctorsList$ = this.reloadDoctorsList.asObservable();

  constructor(private httpClient: HttpClient) { }

  triggerReloadDoctorsList(){
    this.reloadDoctorsList.next();
  }


  getAllStates(): Observable<StatesResponse>{
     return this.httpClient.get<StatesResponse>(baseURL + `states`);
  }

  getAllSpecialities(): Observable<SpecialitiesResponse> {
    return this.httpClient.get<SpecialitiesResponse>(baseURL + `specialities`,{
     
    });
  }

  getCitiesByStateName(stateName: string): Observable<CitiesResponse> {
    return this.httpClient.get<CitiesResponse>(baseURL + `cities-by-state/${stateName}`);
  }

  getCitiesByStateId(stateId: number): Observable<CitiesResponse> {

    return this.httpClient.get<CitiesResponse>(baseURL + `states/${stateId}/cities`);
  }

  //used in admin dashboard
  filterDoctors(doctorName?: string | null, 
                specialityName?: string | null, 
                stateName?: string | null, 
                cityName?: string | null): 
                 Observable<DoctorsResponse> {

    const params: any = {};
    if(doctorName)params.doctorName = doctorName;
    if(specialityName)params.specialityName = specialityName;
    if(stateName)params.stateName = stateName;
    if(cityName)params.cityName = cityName;              

    return this.httpClient.get<DoctorsResponse>(baseURL + `filter-doctors`, {params},);
  }

  //used in patient dashboard
  filterDoctorsClinicSchedule(
  pageNumber: number,
  pageSize: number,
  filters?: DoctorFilter,

): Observable<DoctorClinicScheduleDTOResponse> {
   const params:any = {
      pageNumber: pageNumber - 1,
      pageSize: pageSize,
      ...(filters?.doctorName && {doctorName: filters.doctorName}),
      ...(filters?.specialityName && {specialityName: filters.specialityName}),
      ...(filters?.stateName && {stateName: filters.stateName}),
      ...(filters?.cityName && {cityName: filters.cityName}),
  
   };
         
  return this.httpClient.get<DoctorClinicScheduleDTOResponse>(
    baseURL + `filter-doctor-clinic-schedule`, {params}
  );
}

getBookedDoctor(scheduleId: number): Observable<ApiResponse<BookedDoctorDTO>> {

  const params: any = {
    scheduleId: scheduleId
  };

  return this.httpClient.get<ApiResponse<BookedDoctorDTO>>(baseURL + `booking-info`, {params});
}

chatWithCohereAiModel(message: string, sessionId: string): Observable<any> {
  const params:any = {
    message: message,
    sessionId: sessionId
  };

  return this.httpClient.get(baseURL + `we-care-ai-model/chat`, {params});

}

getDoctorClinicSchedulePage(pageNumber: number, pageSize: number): Observable<DoctorClinicScheduleDTOResponse> {
     
    return this.httpClient.get<DoctorClinicScheduleDTOResponse>(baseURL + `we-care-doctors?pageNumber=${pageNumber - 1}&&pageSize=${pageSize}`, {
      
    });
  }



}
