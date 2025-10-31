import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/authentication/auth-service';
import { MaterialModule } from '../../Material.module';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SignInRequest } from '../models/sign-in-request';
import { StoredUser } from '../models/stored-user';
import { StorageService } from '../../services/storage/storage-service';
import { CommonModule } from '@angular/common';
import { PushNotificationService } from '../../notification/push-notification.service';

@Component({
  selector: 'app-sign-in',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.scss'
})
export class SignInComponent{

 signinForm!: FormGroup;
 hidePassword: boolean = true;

  constructor(private authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router,
              private formBuilder: FormBuilder,
              private storageService: StorageService,
              private pushNotification: PushNotificationService
            
               
  ){

   this.signinForm = this.formBuilder.group({
    email: [null, [Validators.required, Validators.email]],
    password: [null, [Validators.required]]
   })

  }
  

 async onSignIn(event: Event){
    event.preventDefault();

    const notificationToken = await this.pushNotification.getOrRequestToken();

   //attach the token to sign in form  
   const signInRequest: SignInRequest = {
       ... this.signinForm.value,
       notificationToken: notificationToken
   };

   console.log("Sign in Form ", signInRequest);

    this.authService.signIn(signInRequest).subscribe(
      (response)=> {
        console.log("RESPONSE ", response );
        
        if(response.payload?.userId != null){
          const user: StoredUser = {
            message: response.message,
             payload: {
               userId: response.payload.userId,
               userRole: response.payload.userRole
             }
          };
          this.storageService.saveUser(user);
          console.log("user is " , user);
          if(this.storageService.isAdminLoggedIn()){
            this.router.navigateByUrl('/admin/view_doctors');
          }
          else if(this.storageService.isDoctorLoggedIn()){
            this.router.navigateByUrl('/doctor/we_care_doctors');
          } 
          else {
            this.router.navigateByUrl('/patient/view_doctors');
          }

          //success message from backend
          this.snackBar.open(response.message, "Close", {duration: 5000});
        }

      },
      (error)=> {
        const errorMessage = error.error?.message || error.message || 'An unexpected Error occur';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
  }
}
