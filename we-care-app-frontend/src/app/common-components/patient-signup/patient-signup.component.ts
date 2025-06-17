import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../services/authentication/auth-service';
import { PatientSignupRequest } from '../models/patient-signup-request';

@Component({
  selector: 'app-patient-signup',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './patient-signup.component.html',
  styleUrl: './patient-signup.component.scss'
})
export class PatientSignupComponent{
patientSignupForm!: FormGroup;

hidePassword: boolean = true;

constructor(private formBuilder: FormBuilder,
            private snackBar: MatSnackBar,
            private authService: AuthService,
            private router: Router
){

  this.patientSignupForm = this.formBuilder.group({
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.email]],
    mobile: [null, [Validators.required]],
    password: [null, [Validators.required]],
    confirmPassword: [null, [Validators.required]]
  })


}

  onPatientSignUp(){

    console.log(this.patientSignupForm.value);
    const password = this.patientSignupForm.get('password')?.value;
    const confirmPassword = this.patientSignupForm.get('confirmPassword')?.value;

    if(password !== confirmPassword){
      this.snackBar.open("password and confirm password don't match.", "Close", {duration: 5000});

      return;
    }

    const patientSignupRequest: PatientSignupRequest = this.patientSignupForm.value;

    if(this.patientSignupForm.valid){
      this.authService.signupAsPatient(patientSignupRequest).subscribe(
        (response)=> {
          if(response.payload?.id != null){
            this.snackBar.open(response.message, "Close", {duration: 5000});
            this.router.navigateByUrl('/signin');
          }
        },
        (error)=> {
          const errorMessage = error.error?.message || error.message || 'An unexpected error occured';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
      )
    }

  }


  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }

}
