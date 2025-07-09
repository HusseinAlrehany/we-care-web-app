import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule, formatDate } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { DoctorService } from '../../service/doctor.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ScheduleDTOProjection } from '../../../../common-components/models/schedule-dtoprojection';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-view-schedules',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './view-schedules.component.html',
  styleUrl: './view-schedules.component.scss'
})
export class ViewSchedulesComponent implements OnInit{

//columns of the table
  displayedColumns: string [] = [
    'date',
    'startTime',
    'endTime',
    'clinicAddress',
    'actions'
  ];


  pageNumber: number = 1;
  pageSize: number = 2;
  currentPage: number = 0;
  totalElements : number = 0;

  pageSizeOptions: number [] = [2, 4, 6, 8];

  dataSource = new MatTableDataSource<ScheduleDTOProjection>([]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  

  constructor(private doctorService: DoctorService,
              private snackBar: MatSnackBar,
              
  ){}
  ngOnInit(): void {
   
    this.getAllMySchedules(this.pageNumber, this.pageSize);
  }

  onPageChange(event: PageEvent): void {
    this.pageNumber = event.pageIndex + 1;
    this.pageSize = event.pageSize;
    this.getAllMySchedules(this.pageNumber, this.pageSize);

  }
  getAllMySchedules(pageNumber: number, pageSize: number) {
   
    this.doctorService.getAllMySchedules(pageNumber, pageSize).subscribe(
      (res)=> {
          
         this.dataSource.data = res.payload.content;
         this.totalElements = res.payload.page.totalElements;
         this.currentPage = res.payload.page.number;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }


  deleteScheduleById(id: number){

    this.doctorService.deleteScheduleById(id).subscribe(
      (res)=> {
        this.snackBar.open(res.message, "Close", {duration: 3000});
        this.getAllMySchedules(this.pageNumber, this.pageSize);
      },
      (error: HttpErrorResponse)=> {
          if(error.status === 404 && error.message){
            this.snackBar.open(error.error.message, "Close", {duration: 3000});
          }
      }
    )
  }



  //the start and end time is of type LocalTime in backend
  //but we recieve it as string in typescript interface 
  //so we need to manually convert it to Date object
  //then format it using date pipe of angular(as PM or AM) because date pipe works with Date objects not string
  formatTimeToAMPM(timeString: string): string {
  const [hours, minutes] = timeString.split(':');
  const date = new Date();
  date.setHours(+hours, +minutes);

  return formatDate(date, 'hh:mm a', 'en-US');
}

}
