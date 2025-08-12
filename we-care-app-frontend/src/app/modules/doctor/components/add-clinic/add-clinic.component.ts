import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DoctorService } from '../../service/doctor.service';
import { SharedService } from '../../../../common-components/shared-service';
import { Router } from '@angular/router';
import { StateDTO } from '../../../../common-components/models/state-dto';
import { CityDTO } from '../../../../common-components/models/city-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DocClinicDTO } from '../../../../common-components/models/doc-clinic-dto';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-clinic',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-clinic.component.html',
  styleUrl: './add-clinic.component.scss'
})
export class AddClinicComponent implements OnInit{
states: StateDTO[] = [];
cities: CityDTO [] = [];
docClinicDTO!: DocClinicDTO;

  addClinicForm!: FormGroup;

 constructor(private formBuilder: FormBuilder,
             private doctorService: DoctorService,
             private sharedService: SharedService,
             private router: Router,
             private snackBar: MatSnackBar
 ){


  this.addClinicForm = this.formBuilder.group({
  
      cityId: ['', [Validators.required]],
      stateId: ['', [Validators.required]],
      clinicMobile: ['', [Validators.required]],
      address: ['', [Validators.required]],
      
   })

 }


  ngOnInit(): void {
    this.getAllStates();
    
  }
  getAllStates() {
    this.sharedService.getAllStates().subscribe(
      (res)=> {
        this.states = res.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

  onStateChange(stateId: number) {
  
  this.sharedService.getCitiesByStateId(stateId).subscribe(
    (res)=> {
      this.cities = res.payload;
    },
    (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});

    }
  )

}

addClinic(){
  if(this.addClinicForm.valid){
      this.docClinicDTO = this.addClinicForm.value;

  this.doctorService.addClinic(this.docClinicDTO).subscribe({
    next: (res)=> {
      this.snackBar.open(res.message, "Close", {duration: 3000});
      this.router.navigateByUrl('/doctor/add_schedule');
    },

    error: (error: HttpErrorResponse)=> {
      if(error.status === 404 && error.error){
          this.snackBar.open(error.error.mesaage,"Close", {duration: 3000});
      }
    }
  })
  }
}

}
