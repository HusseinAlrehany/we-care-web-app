import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SharedService } from '../shared-service';
import { Router } from '@angular/router';
import { VisitBookingDTO } from '../models/visit-booking-dto';
import { BookedDoctorDTO } from '../models/booked-doctor-dto';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-guest-visit-booking',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './guest-visit-booking.component.html',
  styleUrl: './guest-visit-booking.component.scss'
})
export class GuestVisitBookingComponent implements OnInit{

  visitBookingForm!: FormGroup;
  visitBooking!: VisitBookingDTO;
  bookedDoctor!: BookedDoctorDTO | null;
  docId!: number;
  scId!: number;
  clId!: number;


  constructor(private sharedService: SharedService,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar 
  ){

    this.visitBookingForm = this.formBuilder.group({
       patientName: ['', [Validators.required]],
       patientMobile: ['', [Validators.required]],
       doctorId: ['', [Validators.required]],
       clinicId: ['', [Validators.required]],
       scheduleId: ['', [Validators.required]]
    });


   }

  ngOnInit(): void {

    //extracting the doctorId, clinicId, scheduleId fields from SharedViewDoctors component
    const state = history.state;
    this.docId = state.doctorId;
    this.scId = state.scheduleId;
    this.clId = state.clinicId;

    if(state && state.doctorId && state.scheduleId && state.clinicId){
      this.visitBookingForm.patchValue({
         doctorId: state.doctorId,
         clinicId: state.clinicId,
         scheduleId: state.scheduleId
      });
    } else {
         this.snackBar.open("Booking data is Missing, try again", "Close", {duration:3000});
         this.router.navigate(['/']);
    }

    this.getBookedDoctorWithSchedule(this.scId);
    

  }
  getBookedDoctorWithSchedule(scId: number) {
    throw new Error('Method not implemented.');
  }


}
