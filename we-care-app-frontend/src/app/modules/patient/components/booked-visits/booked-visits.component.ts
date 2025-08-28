import { Component, OnInit, ViewChild } from '@angular/core';
import { PatientService } from '../../service/patient.service';
import { PatientBookedVisitsProjection } from '../../../../common-components/models/patient-booked-visits-projection';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-booked-visits',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './booked-visits.component.html',
  styleUrl: './booked-visits.component.scss'
})
export class BookedVisitsComponent implements OnInit{

  displayedColumns: string [] = [
    'date',
    'doctorName',
    'fullAddress',
    'patientMobile',
    'patientName',
    'specialityName'];
  
  dataSource = new MatTableDataSource<PatientBookedVisitsProjection>([]);

  bookedVisits: PatientBookedVisitsProjection [] = [];


  constructor(private patientService: PatientService,
              private snackBar: MatSnackBar
              
  ){}
  ngOnInit(): void {
    this.getBookedVisits();
    
  }
  getBookedVisits() {
     this.patientService.getPatientBookedVisits().subscribe({
      next: (res)=> {
        //?? this is nullish coalesing operator
        // when a ?? b means if a not null or undefined , use a
        //if a is null or undefined, use b
         this.bookedVisits = res.payload?? [];
         console.log("My booked visits are ", res);
         this.dataSource.data = res.payload?? [];
      },
      error: (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
     })
  }

}
