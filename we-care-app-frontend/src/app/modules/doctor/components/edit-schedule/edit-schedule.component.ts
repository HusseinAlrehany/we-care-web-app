import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { DoctorService } from '../../service/doctor.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ScheduleDTO } from '../../../../common-components/models/schedule-dto';
import { ScheduleDTOSTR } from '../../../../common-components/models/schedule-dtostr';

@Component({
  selector: 'app-edit-schedule',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule],
  templateUrl: './edit-schedule.component.html',
  styleUrl: './edit-schedule.component.scss'
})
export class EditScheduleComponent implements OnInit{

  updateScheduleForm!: FormGroup;
  scheduleDTOSTR!: ScheduleDTOSTR;

  scheduleId: number = this.activatedRoute.snapshot.params['id'];


constructor(private formBuilder: FormBuilder,
            private snackBar: MatSnackBar,
            private router: Router,
            private doctorService: DoctorService,
            private activatedRoute: ActivatedRoute
){

  this.updateScheduleForm = this.formBuilder.group({
    scDate: ['', [Validators.required]],
    scStartTime: ['', [Validators.required]],
    scEndTime: ['', [Validators.required]]
  });

}

  ngOnInit(): void {

    this.getScheduleById();

  }
  getScheduleById() {
    this.doctorService.getScheduleById(this.scheduleId).subscribe(
      (res)=> {

        this.updateScheduleForm.patchValue(res.payload);
      

      },(error: HttpErrorResponse)=>{
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.mesaage, "Close", {duration: 5000});
        }
      }
    )
  }


  updateSchedule(){

   const formValue = this.updateScheduleForm.value;

  // Format date to "yyyy-MM-dd" converting the string input of date, time to date object
  //before submitting to backend to be parsed as LocalDate, LocalTime
  const selectedDate = new Date(formValue.scDate);

  const formattedDate = selectedDate.getFullYear() + '-' +
    String(selectedDate.getMonth() + 1).padStart(2, '0') + '-' +
    String(selectedDate.getDate()).padStart(2, '0');

  this.scheduleDTOSTR = {
    ...formValue,
    scDate: formattedDate
  };
     
    this.doctorService.updateSchedule(this.scheduleId, this.scheduleDTOSTR).subscribe(
       (res)=> {
         this.snackBar.open(res.message, "Close", {duration: 5000});
         this.router.navigateByUrl('/doctor/view_schedules');
       },
       (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
       }
    )
  }

}
