import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedService } from '../shared-service';
import { ReviewDTOResponseProjection } from '../models/review-dtoresponse-projection';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-doctor-reviews',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './doctor-reviews.component.html',
  styleUrl: './doctor-reviews.component.scss'
})
export class DoctorReviewsComponent implements OnInit{

  doctorReviews: ReviewDTOResponseProjection [] = [];
  doctorId: number = this.route.snapshot.params['id'];
  pageNumber: number = 1;
  pageSize: number = 10;
  totalElements: number = 0;
  currentPage: number = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSizeOptions: number [] = [2,4,8,12,16];

  readonly Math = Math;

 constructor(private sharedService: SharedService,
             private route: ActivatedRoute,
             private snackBar: MatSnackBar
             
 ){}


  ngOnInit(){
     this.getReviewsByDoctorId(this.doctorId, this.pageNumber, this.pageSize);
  }



 getReviewsByDoctorId(doctorId: number, pageNumber: number, pageSize: number) {
   
  this.sharedService.getReviewsByDoctorId(doctorId, pageNumber, pageSize).subscribe({
    //next here is the callback of RXJS subscribe() it runs if the response is successfull from backend
     next: (res)=> {
      //? is(optional chaining operator) check if the payload is not null or not undefined it will access it otherwise return undefined without throwing error
      //?? is (nullish operator) it gives the default value (0) if the left hand side (res.payload?.content??) is null or undefined. 
      console.log("Reviews are", res);
       this.doctorReviews = res.payload?.content ?? [];
       this.totalElements = res.payload?.totalElements ?? 0;
       this.currentPage = res.payload?.number ?? 0;
     },
     error: (error: HttpErrorResponse)=> {
       if(error.status === 404 && error.error){
        this.snackBar.open(error.error.message, "Close", {duration: 3000});
       }
     }
  });

     
  }

  onPageChange(event: PageEvent){
     this.pageNumber = event.pageIndex + 1;
     this.pageSize = event.pageSize;
     this.getReviewsByDoctorId(this.doctorId, this.pageNumber, this.pageSize);
  }

}
