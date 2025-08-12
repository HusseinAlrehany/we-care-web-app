import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { ClinicDTO } from '../../../../common-components/models/clinic-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-all-clinics',
  standalone: true ,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './all-clinics.component.html',
  styleUrl: './all-clinics.component.scss'
})
export class AllClinicsComponent implements OnInit{

  //columns of the table
  displayedColumns: string [] = [
    'clinicLocation',
    'clinicMobile',
    'address',
    'doctorName',
    'cityName',
    'stateName',
    'actions'
  ];

  dataSource = new MatTableDataSource<ClinicDTO>([]);


  pageNumber: number = 1;
  pageSize: number = 6;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSizeOptions: number[] = [2,4,6,8];


  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private adminService: AdminService,
              private snackBar: MatSnackBar

  ){}
  ngOnInit(): void {
     this.getAllClinics(this.pageNumber, this.pageSize);
  }
  getAllClinics(pageNumber: number, pageSize: number) {
    this.adminService.getClinicPage(pageNumber, pageSize ).subscribe(
      (res)=> {
        console.log(res);
        this.dataSource.data = res.payload.content;
        this.totalElements = res.payload.totalElements;
        this.currentPage = res.payload.number;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

  onPageChange(event: PageEvent): void {

    this.pageSize = event.pageSize;
    this.pageNumber = event.pageIndex + 1;
    this.getAllClinics(this.pageNumber, this.pageSize);
  }

  deleteClinicById(clinicId: number){
    this.adminService.deleteClinicById(clinicId).subscribe(
      (res)=> {
        this.snackBar.open(res.message, "Close", {duration: 5000});

        //clear the table before refetching the data
        this.dataSource.data = [];
        this.getAllClinics(this.pageNumber, this.pageSize);
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

}
