import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DoctorService } from '../../service/doctor.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ScheduleDTO } from '../../../../common-components/models/schedule-dto';
import { ClinicDTOProjection } from '../../../../common-components/models/clinic-dtoprojection';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-schedule',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-schedule.component.html',
  styleUrl: './add-schedule.component.scss'
})
export class AddScheduleComponent implements OnInit{

  scheduleForm!: FormGroup;
  myClinics: ClinicDTOProjection [] = [];
  scheduleDTO!: ScheduleDTO;
  

  constructor(private doctorService: DoctorService,
              private formBuilder: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar

  ){

    this.scheduleForm = this.formBuilder.group({
      clinicId: ['', [Validators.required]],
      startTime: ['', [Validators.required]],
      endTime: ['', [Validators.required]],
      date: ['', [Validators.required]]
    });

  }
  ngOnInit(): void {
    this.getMyClinicsByUserId();
  }
  getMyClinicsByUserId() {
     this.doctorService.getMyClinicsByUserId().subscribe(
      (res)=> {
        this.myClinics = res.payload;
        console.log("My Clinics are => ", this.myClinics);
      },
      (error)=> {
        const errorMessage = error?.error?.mesaage || error?.errorMessage || 'an unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
     )
  }

  addSchedule(){
   
   const formValue = this.scheduleForm.value;

   //to fix that the date is one day behind because automatic conversion to UTC this shifts a day
   // Format date to "yyyy-MM-dd" converting the string input of date, time to date object
   const selectedDate: Date = formValue.date;
   const formattedDate = selectedDate.getFullYear() + '-' +
                        String(selectedDate.getMonth() + 1).padStart(2, '0') + '-' +
                        String(selectedDate.getDate()).padStart(2, '0');
   this.scheduleDTO = {
     ...formValue,
     date: formattedDate
   };

     console.log(' Final DTO sent to backend:', this.scheduleDTO);

    this.doctorService.addSchedule(this.scheduleDTO).subscribe(
      (res)=> {
        this.snackBar.open(res.message, "Close", {duration: 5000});
        this.router.navigateByUrl('/doctor/view_schedules');
      },
      (error: HttpErrorResponse)=> {
        
        if(error.status === 409 && error.error){
          this.snackBar.open(error.error.message,"Close", {duration: 5000});
        }
      }
    )
  }


   

}
