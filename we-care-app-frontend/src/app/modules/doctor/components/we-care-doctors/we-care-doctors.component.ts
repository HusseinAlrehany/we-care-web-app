import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DoctorService } from '../../service/doctor.service';
import { SameSpecialityDoctorsDTO } from '../../../../common-components/models/same-speciality-doctors-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-we-care-doctors',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './we-care-doctors.component.html',
  styleUrl: './we-care-doctors.component.scss'
})
export class WeCareDoctorsComponent implements OnInit{

  sameSpecialityDoctors: SameSpecialityDoctorsDTO[] = [];

  pageNumber: number = 1;
  pageSize: number = 6;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSizeOptions: number[] = [2,4,6,8];


 @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private doctorService: DoctorService,
              private snackBar: MatSnackBar,
             

  ){

    
  }
  ngOnInit(): void {
    this.getSameSpecialityDoctors(this.pageNumber, this.pageSize);
  }

  
onPageChange(event: PageEvent): void{
   
   this.pageNumber = event.pageIndex + 1;
   this.pageSize = event.pageSize;
   this.getSameSpecialityDoctors(this.pageNumber, this.pageSize);


}

  getSameSpecialityDoctors(pageNumber: number, pageSize:number) {
    
    this.doctorService.getSameSpecialityDoctor(pageNumber, pageSize).subscribe(
      (res)=> {

        this.sameSpecialityDoctors = res.payload.content;
        this.totalElements = res.payload.totalElements;
        this.currentPage = res.payload.pageNumber;
        console.log("Same speciality Doctors are" + this.sameSpecialityDoctors);

      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000}); 
      }
    )
  }

}
