import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { SpecialityDetailsInfo } from '../../../../common-components/models/speciality-details-info';

@Component({
  selector: 'app-speciality-details',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './speciality-details.component.html',
  styleUrl: './speciality-details.component.scss'
})
export class SpecialityDetailsComponent implements OnInit{

  displayedColumns: string [] = [
  'fullName',
  'doctorEmail',
  'specialityName',
  'clinicMobile',
  'fullAddress'
  ];

  dataSource = new MatTableDataSource<SpecialityDetailsInfo>([]);

  specialityId: number = this.activatedRoute.snapshot.params['id'];

//specialityDetails: SpecialityDetailsDTO [] = [];
@ViewChild(MatPaginator) paginator! : MatPaginator;

totalElements: number = 0;
pageSize:number = 1;
pageNumber: number = 1;
currentPage: number = 0;
pageSizeOptions: number []= [2,4,6,8];



  constructor(private adminService: AdminService,
              private snakBar: MatSnackBar,
              private activatedRoute: ActivatedRoute
              
  ){}
  ngOnInit(): void {
    this.getSpecialityDetails(this.specialityId, this.pageNumber, this.pageSize);
  }
 
  onPageChange(event: PageEvent): void{
    this.pageSize = event.pageSize;
    this.pageNumber = event.pageIndex + 1;
    this.getSpecialityDetails(this.specialityId, this.pageNumber, this.pageSize);


  }

  getSpecialityDetails(specialityId: number, pageNumber: number, pageSize: number) {
    this.adminService.getSpecialityDetailsById(specialityId, pageNumber, pageSize).subscribe(
      (res)=> {
        
        this.totalElements = res.payload.totalElements;
        this.currentPage = res.payload.number;
        
        this.dataSource.data = res.payload.specialityDetailsInfoProjectionList;
        this.dataSource.paginator = this.paginator;

      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
        this.snakBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }
}
