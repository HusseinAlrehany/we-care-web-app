import { Component, OnInit } from '@angular/core';
import { SharedService } from '../shared-service';
import { DoctorDetailsViewproj } from '../models/doctor-details-viewproj';
import { ActivatedRoute } from '@angular/router';
import { MaterialModule } from '../../Material.module';
import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-doctor-details',
  standalone: true,
  imports: [MaterialModule, CommonModule],
  templateUrl: './doctor-details.component.html',
  styleUrl: './doctor-details.component.scss'
})
export class DoctorDetailsComponent implements OnInit{

  doctorId: number = this.activatedRoute.snapshot.params['id'];

  doctorDetailsView: DoctorDetailsViewproj | null = null;


  constructor(private sharedService: SharedService,
              private activatedRoute:ActivatedRoute,
              private snackBar: MatSnackBar
  ){}

  ngOnInit(): void {

    this.getDoctorDetailsView(this.doctorId);
    
  }
  getDoctorDetailsView(doctorId: number) {
    this.sharedService.getDoctorDetailsView(doctorId).subscribe({
      next: (res)=> {
        console.log(res);
        this.doctorDetailsView = res.payload ?? null;
      },
      error: (error: HttpErrorResponse)=> {
           this.snackBar.open(error.error.message, "Close", {duration: 3000});
      }
    })
  }

}
