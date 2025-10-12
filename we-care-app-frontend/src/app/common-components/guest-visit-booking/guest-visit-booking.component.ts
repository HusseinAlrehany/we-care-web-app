import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { CommonModule, formatDate } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SharedService } from '../shared-service';
import { Router } from '@angular/router';
import { VisitBookingDTO } from '../models/visit-booking-dto';
import { BookedDoctorDTO } from '../models/booked-doctor-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { PatientService } from '../../modules/patient/service/patient.service';

@Component({
  selector: 'app-guest-visit-booking',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './guest-visit-booking.component.html',
  styleUrl: './guest-visit-booking.component.scss'
})
export class GuestVisitBookingComponent implements OnInit{

  guestBookingForm!: FormGroup;
  visitBooking!: VisitBookingDTO;
  bookedDoctor!: BookedDoctorDTO | null;
  docId!: number;
  scId!: number;
  clId!: number;


  constructor(private sharedService: SharedService,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private patientService: PatientService
  ){

    this.guestBookingForm = this.formBuilder.group({
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
      this.guestBookingForm.patchValue({
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
     if(scId != null){
      this.sharedService.getBookedDoctor(scId).subscribe({
        next: (res)=> {
          this.bookedDoctor = res.payload;
          console.log("BOOKED DOCTOR INFO", res);
        },
        error: (error: HttpErrorResponse)=> {
          if(error.status === 404 && error.error){
              this.snackBar.open(error.error.message, "Close", {duration: 3000});
          }
        }
      });
     }

  }

  bookAvisit(){
    if(this.guestBookingForm.valid){
      this.visitBooking = this.guestBookingForm.value;

      this.patientService.bookAvisit(this.visitBooking).subscribe({
        next: (res)=> {
          console.log("Booked Visit details", res);
          this.router.navigateByUrl('/view_doctors');
        },
        error: (error: HttpErrorResponse)=> {
          if(error.status === 404 || error.status === 409 && error.error){
            this.snackBar.open(error.error.message, "Close", {duration: 3000});
            this.router.navigateByUrl('/view_doctors');
          }
        }
      });
    }
  }


  //convert to AM, PM
  formatTimeToAMPM(timeString: string): string{

    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(+hours, +minutes);
    
    return formatDate(date, 'hh:mm a', 'en-US');
  }

  onCancel(){
    this.router.navigateByUrl('/view_doctors');
  }





}
