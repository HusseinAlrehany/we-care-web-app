import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { StatesResponse } from './models/states-response';
import { CitiesResponse } from './models/cities-response';
import { DoctorsResponse } from './models/doctors-response';

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

  getCitiesByStateName(stateName: string): Observable<CitiesResponse> {
    return this.httpClient.get<CitiesResponse>(baseURL + `cities-by-state/${stateName}`);
  }

  getCitiesByStateId(stateId: number): Observable<CitiesResponse> {

    return this.httpClient.get<CitiesResponse>(baseURL + `states/${stateId}/cities`);
  }

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

    return this.httpClient.get<DoctorsResponse>(baseURL + `filter-doctors`, {params});
  }
}
