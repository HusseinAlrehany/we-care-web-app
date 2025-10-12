import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule, formatDate } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DoctorService } from '../../service/doctor.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DoctorPreviousVisitDtoProjection } from '../../../../common-components/models/doctor-previous-visit-dto-projection';
import { HttpErrorResponse } from '@angular/common/http';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-previous-visits',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './previous-visits.component.html',
  styleUrl: './previous-visits.component.scss'
})
export class PreviousVisitsComponent implements OnInit{
visitCard!: FormGroup;
doctorPreviousVisits: DoctorPreviousVisitDtoProjection[] = [];
pageNumber: number = 1;
pageSize: number = 5;
totalElements: number = 0
currentPage: number = 0;
totalPages: number = 0;
pageSizeOptions: number[] = [5, 10, 20];

@ViewChild(MatPaginator) paginator!: MatPaginator;

constructor(private doctorService: DoctorService,
            private formBuider: FormBuilder,
            private router: Router,
            private snackBar: MatSnackBar 
){}

  ngOnInit(): void {
    this.getDoctorPreviousVisits(this.pageNumber, this.pageSize);
  }
  getDoctorPreviousVisits(pageNumber: number, pageSize: number) {
    this.doctorService.getDoctorPreviousVisits(pageNumber, pageSize).subscribe({
      next: (res)=> {
        this.doctorPreviousVisits = res.payload.content;

        this.totalElements = res.payload.totalElements;
        this.currentPage = this.pageNumber;
        console.log("response", res);
      },
      error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
        }
      }
    });
  }

  onPageChange(event: PageEvent): void{
     this.pageNumber = event.pageIndex + 1;
     this.pageSize = event.pageSize;

     this.getDoctorPreviousVisits(this.pageNumber, this.pageSize);

  }

  formatTimeToAMPM(timeString: string): string{

    const [hours, minutes] = timeString.split(':');

    const date = new Date();
    date.setHours(+hours, +minutes);

    return formatDate(date, 'hh:mm a', 'en-US');

  }

}
