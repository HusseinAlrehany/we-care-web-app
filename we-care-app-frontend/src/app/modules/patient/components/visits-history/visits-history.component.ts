import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { PatientService } from '../../service/patient.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { PatientBookedVisitsProjection } from '../../../../common-components/models/patient-booked-visits-projection';
import { AdminRoutingModule } from "../../../admin/admin-routing.module";

@Component({
  selector: 'app-visits-history',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, AdminRoutingModule],
  templateUrl: './visits-history.component.html',
  styleUrl: './visits-history.component.scss'
})
export class VisitsHistoryComponent implements OnInit{

 displayedColumns: string [] = [
   'date',
   'doctorName',
   'fullAddress',
   'patientMobile',
   'patientName',
   'specialityName',
   'actions'
 ];

 dataSource = new MatTableDataSource<PatientBookedVisitsProjection>([]);


constructor(private patientService: PatientService,
            private snackBar: MatSnackBar
){

}

  ngOnInit(): void {
    this.getPatientCheckedVisits();
  }
  getPatientCheckedVisits() {
    this.patientService.getPatientCheckedVisits().subscribe({
      next: (res)=> {
          this.dataSource.data = res.payload ?? [];
      },
      error: (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occures';
        this.snackBar.open(errorMessage, "Close", {duration: 3000});
      }
    });
  }

}
