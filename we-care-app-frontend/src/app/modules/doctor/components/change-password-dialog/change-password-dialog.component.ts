import { Component } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ChangePasswordRequest } from '../../../../common-components/models/change-password-request';
import { DoctorService } from '../../service/doctor.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../../services/authentication/auth-service';
import { StorageService } from '../../../../services/storage/storage-service';
import { StoredUser } from '../../../../common-components/models/stored-user';

@Component({
  selector: 'app-change-password-dialog',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './change-password-dialog.component.html',
  styleUrl: './change-password-dialog.component.scss'
})
export class ChangePasswordDialogComponent {

changePasswordForm!: FormGroup;
changePasswordRequest!: ChangePasswordRequest;

hideOldPassword: boolean = true;
hideNewPassword: boolean = true;
hideConfirmPassword: boolean = true;

//componenets are designed to be used with templates not for injection
//but services can be injected and angular support this
  constructor(private formBuilder: FormBuilder,
              public dialogRef: MatDialogRef<ChangePasswordDialogComponent>,
              private doctorService: DoctorService,
              private router: Router,
              private snackBar:MatSnackBar,
              private authService: AuthService,
              private storageService: StorageService
              
  ){

   this.changePasswordForm = this.formBuilder.group({
    oldPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
    confirmPassword: ['', Validators.required]
   }, {validators: this.passwordMatchValidator });

  }

  passwordMatchValidator(form: FormGroup) {
     const newPassword = form.get('newPassword')?.value;
     const confirmPassword = form.get('confirmPassword')?.value;

     return newPassword === confirmPassword ? null : {passwordMismatch: true};
  }

  changePassword(){
    if(this.changePasswordForm.valid){
      //this.changePasswordRequest = this.changePasswordForm.value;

      this.doctorService.changePassword(this.changePasswordForm.value).subscribe({
        next: (res)=> {
           this.snackBar.open(res.message, "Close", {duration: 5000});
           this.dialogRef.close();

           //delay the signout() redirection or routing untill te user see the success message.
           setTimeout(()=> {
             this.signOut();    
           }, 2500);
                      
        }, 
        error: (error: HttpErrorResponse)=> {
           if(error.status === 404 && error.error){
               this.snackBar.open(error.error.message, "Close", {duration: 3000});
           }
        }
      });
    }
  }

  signOut(){

    this.authService.logout().subscribe({
      next: (res)=> {
        const user: StoredUser = {
          message: res.message,
          payload: null
        };

        this.snackBar.open(res.message, "Close", {duration: 3000});
        this.storageService.clearUserData();
        this.router.navigateByUrl('/signin');

      },
      error: (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected Error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 3000});
        this.storageService.clearUserData();
        this.router.navigateByUrl('/signin'); 
      }
    });
  }


}
