import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DoctorClinicScheduleDTOResponse } from '../../../common-components/models/doctor-clinic-schedule-dtoresponse';
import { ApiResponse } from '../../../common-components/api-response/api-response';
import { VisitBookingDTO } from '../../../common-components/models/visit-booking-dto';
import { PatientBookedVisitsProjection } from '../../../common-components/models/patient-booked-visits-projection';
import { ReviewDTORequest } from '../../../common-components/models/review-dtorequest';

const baseURL = 'http://localhost:8080/we-care/patient/';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private httpClient: HttpClient) { }


  /*getDoctorClinicSchedulePage(pageNumber: number, pageSize: number): Observable<DoctorClinicScheduleDTOResponse> {
     
    return this.httpClient.get<DoctorClinicScheduleDTOResponse>(baseURL + `we-care-doctors?pageNumber=${pageNumber - 1}&&pageSize=${pageSize}`, {
      withCredentials: true,
    });
  }*/

  bookAvisit(visitBookingDTO: VisitBookingDTO): Observable<ApiResponse<string>>{

    return this.httpClient.post<ApiResponse<string>>(`http://localhost:8080/book-visit`, visitBookingDTO, {
      withCredentials: true,
    });
  }

  getPatientBookedVisits(): Observable<ApiResponse<PatientBookedVisitsProjection[]>> {
    return this.httpClient.get<ApiResponse<PatientBookedVisitsProjection[]>>(baseURL + `booked-visits`, {
      withCredentials: true,
    });
  }

  getPatientCheckedVisits(): Observable<ApiResponse<PatientBookedVisitsProjection[]>> {

    return this.httpClient.get<ApiResponse<PatientBookedVisitsProjection[]>>(baseURL + `checked-visits`, {
      withCredentials: true,
    });
  }

  addReview(review: ReviewDTORequest): Observable<ApiResponse<string>>{
    return this.httpClient.post<ApiResponse<string>>(baseURL + `add-review`, review, {
      withCredentials: true,
    });
  }

}
