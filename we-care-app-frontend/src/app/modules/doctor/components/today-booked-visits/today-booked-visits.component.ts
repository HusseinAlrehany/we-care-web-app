import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, formatDate } from '@angular/common';
import { DoctorPreviousVisitDtoProjection } from '../../../../common-components/models/doctor-previous-visit-dto-projection';
import { DoctorService } from '../../service/doctor.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-today-booked-visits',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './today-booked-visits.component.html',
  styleUrl: './today-booked-visits.component.scss'
})
export class TodayBookedVisitsComponent implements OnInit{

todaysVisits: DoctorPreviousVisitDtoProjection [] = [];  

constructor(private doctorService: DoctorService,
            private snackBar: MatSnackBar
){}

  ngOnInit(): void {
    this.getDoctorsTodayVisits();
  }
  getDoctorsTodayVisits() {
    this.doctorService.getDoctorTodaysVisit().subscribe({
      next: (res)=> {
          this.todaysVisits = res.payload ?? [];
          console.log("todays visits are ", res);
      }, 
      error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
        }
      }
    });
  }


formatTimeToAMPM(timeString: string): string {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(+hours, +minutes);
    return formatDate(date, 'hh:mm a', 'en-US');
}

}
