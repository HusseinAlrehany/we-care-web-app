import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ScheduleDTO } from '../../../common-components/models/schedule-dto';
import { ScheduleDTOResponse } from '../../../common-components/models/schedule-dtoresponse';
import { StorageService } from '../../../services/storage/storage-service';
import { SameSpecialityPageResponse } from '../../../common-components/models/same-speciality-page-response';
import { ClinicDTOProjectionResponse } from '../../../common-components/models/clinic-dtoprojection-response';
import { ScheduleDTOProjectionResponse } from '../../../common-components/models/schedule-dtoprojection-response';
import { ScheduleDTOSTR } from '../../../common-components/models/schedule-dtostr';

const baseURL = "http://localhost:8080/we-care/doctor/";


@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  constructor(private httpClient: HttpClient,
              private storageService: StorageService
  ) { }



  addSchedule(scheduleDTO: ScheduleDTO): Observable<ScheduleDTOResponse>{
       
    return this.httpClient.post<ScheduleDTOResponse>(baseURL + `add-schedule`, scheduleDTO, {
          withCredentials: true,
    });
 
  }
  getSameSpecialityDoctor(pageNumber: number, pageSize:number):  Observable<SameSpecialityPageResponse> {
    return this.httpClient.get<SameSpecialityPageResponse>(baseURL + `my-speciality?pageNumber=${pageNumber - 1}&pageSize=${pageSize}`, {
      withCredentials: true,
    });
  } 

  addClinicSchedule(scheduleDTO: ScheduleDTO): Observable<ScheduleDTOResponse>{
    return this.httpClient.post<ScheduleDTOResponse>(baseURL + `add-schedule`, scheduleDTO, {
      withCredentials: true,
    });
  }

  getMyClinicsByUserId(): Observable<ClinicDTOProjectionResponse> {
    return this.httpClient.get<ClinicDTOProjectionResponse>(baseURL + `my-clinics`, {
      withCredentials: true,
    });
  }

  getAllMySchedules(pageNumber: number, pageSize: number): Observable<ScheduleDTOProjectionResponse>{
    return this.httpClient.get<ScheduleDTOProjectionResponse>(baseURL + `schedules?pageNumber=${pageNumber - 1}&pageSize=${pageSize}`, {
      withCredentials: true,
    });
  }

  deleteScheduleById(scheduleId: number): Observable<any> {

    return this.httpClient.delete(baseURL + `schedule/${scheduleId}`, {
      withCredentials: true,
    });
  }

  getScheduleById(scheduleId: number): Observable<ScheduleDTOResponse> {

    return this.httpClient.get<ScheduleDTOResponse>(baseURL + `schedule/${scheduleId}`, {
      withCredentials: true,
    });
  }

  updateSchedule(scheduleId: number, scheduleDTO: ScheduleDTOSTR): Observable<any> {

    return this.httpClient.put<any>(baseURL + `edit-schedule/${scheduleId}`, scheduleDTO, {
      withCredentials: true,
    });
  }

  
}
