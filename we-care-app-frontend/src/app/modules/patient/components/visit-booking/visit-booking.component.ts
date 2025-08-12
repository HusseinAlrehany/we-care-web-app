import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../service/patient.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VisitBookingDTO } from '../../../../common-components/models/visit-booking-dto';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule, formatDate } from '@angular/common';
import { SharedService } from '../../../../common-components/shared-service';
import { HttpErrorResponse } from '@angular/common/http';
import { BookedDoctorDTO } from '../../../../common-components/models/booked-doctor-dto';

@Component({
  selector: 'app-visit-booking',
  standalone: true ,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './visit-booking.component.html',
  styleUrl: './visit-booking.component.scss'
})
export class VisitBookingComponent implements OnInit {

  bookingForm!: FormGroup;
  visitBooking!: VisitBookingDTO;
  bookedDoctor!: BookedDoctorDTO | null;
  scId!: number;
  docId!: number;
  
  constructor(private patientService: PatientService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
              private sharedService: SharedService
  ){

    this.bookingForm = this.formBuilder.group({
      patientName: ['', [Validators.required]],
      patientMobile: ['', [Validators.required]],
      doctorId: ['', [Validators.required]],
      clinicId: ['', [Validators.required]],
      scheduleId: ['', [Validators.required]]
    });
  }
  ngOnInit(): void {

    //extracting the 3 ids fields (doctorId, clinicId, scheduleId) sent from the other component
     const state = history.state;
     console.log("state is ", state);
     this.scId = state.scheduleId;
     this.docId = state.doctorId;
     console.log("Schedule ID", this.scId);
     
     if(state && state.doctorId && state.clinicId && state.scheduleId){
      this.bookingForm.patchValue({
        doctorId: state.doctorId,
        clinicId: state.clinicId,
        scheduleId: state.scheduleId
      });
     } else {
      this.snackBar.open("Booking data is Missing, try again", "Close", {duration: 3000});
      this.router.navigate(['/']);
     }

     this.getBookedDoctorWithSchedule(this.scId);
    
  }
  getBookedDoctorWithSchedule(scId: number) {
    
    if(scId != null){
      this.sharedService.getBookedDoctor(scId).subscribe({
        next: (res)=> {
           this.bookedDoctor = res.payload;
           //this.spcialityName = res.specialityName;
           console.log("Booked Doctor INFO", res);

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

    if(this.bookingForm.valid){
       this.visitBooking = this.bookingForm.value;

    this.patientService.bookAvisit(this.visitBooking).subscribe({
      next: (res)=> {
        
          this.snackBar.open(res.message, "Close", {duration: 3000});
          this.router.navigateByUrl('/patient/my_visits');
        
      },
      error: (error: HttpErrorResponse)=> {
        if(error.status === 404 || error.status === 409 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});

          return;
        }
      }
    })

    }
   
  }

  onCancel(){
    this.router.navigateByUrl('/patient/view_doctors');
  }


  //to convert to AM, PM
  formatTimeToAMPM(timeString: string): string {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(+hours, +minutes);
  
    return formatDate(date, 'hh:mm a', 'en-US');
  }
  

}
