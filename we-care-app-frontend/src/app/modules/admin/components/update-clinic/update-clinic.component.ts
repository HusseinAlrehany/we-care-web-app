import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ShortDoctorsDTO } from '../../../../common-components/models/short-doctors-dto';
import { StateDTO } from '../../../../common-components/models/state-dto';
import { CityDTO } from '../../../../common-components/models/city-dto';
import { SharedService } from '../../../../common-components/shared-service';

@Component({
  selector: 'app-update-clinic',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './update-clinic.component.html',
  styleUrl: './update-clinic.component.scss'
})
export class UpdateClinicComponent implements OnInit{

  updateClinicForm!: FormGroup;
  doctors: ShortDoctorsDTO [] = [];
  states: StateDTO [] = [];
  cities: CityDTO [] = [];

  clinicId: number = this.activatedRoute.snapshot.params['id'];


  constructor(private adminService: AdminService,
              private sharedService:SharedService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder,
              private activatedRoute: ActivatedRoute
  ){

    this.updateClinicForm = this.formBuilder.group({
        clinicMobile: ['', [Validators.required]],
        address: ['', [Validators.required]],
        doctorId: ['', [Validators.required]],
        stateId: ['', [Validators.required]],
        cityId: ['', [Validators.required]]

    })

  }
  ngOnInit(): void {
    this.getAllShortDoctors();
    this.getAllStates();
    this.getClinicById(this.clinicId);
   
  }

  //to repopulate the form with the values
  getClinicById(clinicId: number) {
    this.adminService.getClinicById(clinicId).subscribe(
      (res)=> {
        this.updateClinicForm.patchValue(res);
      },
      (error)=> {
         const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
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
      const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
      this.snackBar.open(errorMessage, "Close", {duration: 5000});
    }
   )
  }

  onStateChange(stateId: number){
      this.cities = [];
      this.sharedService.getCitiesByStateId(stateId).subscribe(
        (res)=> {
          this.cities = res.payload;
        },
        (error)=> {
          const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
      )
  }
  getAllShortDoctors() {
    this.adminService.getAllShortDoctors().subscribe(
      (res)=> {
        this.doctors = res.payload;
      
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});

      }
    )
  }

  updateClinic(){
    this.adminService.updateClinic(this.updateClinicForm.value, this.clinicId).subscribe(
      (res)=> {
        this.snackBar.open(res.message, "Close", {duration: 5000});
        this.router.navigateByUrl('/admin/all_clinics');
      },
      (error)=> {
        const errorMerssage =error?.error?.message || error?.errorMessage || 'un expected error occured';
        this.snackBar.open(errorMerssage, "Close", {duration: 5000}); 
      }
    )
  }

}
