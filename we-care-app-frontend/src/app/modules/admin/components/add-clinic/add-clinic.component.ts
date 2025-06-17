import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { SharedService } from '../../../../common-components/shared-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ShortDoctorsDTO } from '../../../../common-components/models/short-doctors-dto';
import { CityDTO } from '../../../../common-components/models/city-dto';
import { StateDTO } from '../../../../common-components/models/state-dto';

@Component({
  selector: 'app-add-clinic',
  standalone: true ,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-clinic.component.html',
  styleUrl: './add-clinic.component.scss'
})
export class AddClinicComponent implements OnInit{

  addClinicForm!: FormGroup;

  shortDoctorsList: ShortDoctorsDTO [] = [];
  cities: CityDTO [] = [];
  states: StateDTO [] = [];
  

  constructor(private adminservice: AdminService,
              private sharedService: SharedService,
              private snackBar: MatSnackBar,
              private router: Router,
              private formBuilder: FormBuilder
  ){
 this.addClinicForm = this.formBuilder.group({

    doctorId: ['', [Validators.required]],
    cityId: ['', [Validators.required]],
    stateId: ['', [Validators.required]],
    clinicMobile: ['', [Validators.required]],
    address: ['', [Validators.required]],
    
 })


  }
  ngOnInit(): void {
    this.getAllStates();
    this.getShortDoctors();

    //dynamically load cities when state changes
    
  }

  addClinic() {
    if(this.addClinicForm.valid){
      this.adminservice.addClinic(this.addClinicForm.value).subscribe(
        (res)=> {
          this.snackBar.open(res.message, "Close", {duration: 5000});
          this.router.navigateByUrl('/admin/all_clinics');
        },
        (error)=> {
          const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
      )
    }
  }
  getShortDoctors() {
    this.adminservice.getAllShortDoctors().subscribe(
      (res)=> {
        this.shortDoctorsList = res.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }
  onStateChange(stateId: number) {
    this.cities = [];
    this.sharedService.getCitiesByStateId(stateId).subscribe(
      (res)=> {
        this.cities = res.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
        
      }
    )
  }
  getAllStates() {
    this.sharedService.getAllStates().subscribe(
      (res)=> {
        this.states = res.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occurs';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

}
