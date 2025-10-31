import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/authentication/auth-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SpecialitiesDTO } from '../models/specialities-dto';
import { PushNotificationService } from '../../notification/push-notification.service';

@Component({
  selector: 'app-doctor-signup',
  standalone: true ,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './doctor-signup.component.html',
  styleUrl: './doctor-signup.component.scss'
})
export class DoctorSignupComponent implements OnInit{
  
  doctorSignupForm!: FormGroup;

  hidePassword: boolean = true;

  listOfSpecialities: SpecialitiesDTO [] = [];

  selectedMedicalCardFile?: File | null;
  selectedDoctorPhotoFile?: File | null;


  medicalCardPreview?: string | ArrayBuffer | null;
  doctorPhotoPreview?: string | ArrayBuffer | null;

  constructor(private authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router,
              private formBuilder: FormBuilder,
              private notificationService: PushNotificationService
              
  ){}

onSelectedMedicalFile(event: any){
   this.selectedMedicalCardFile = event.target.files[0];
   this.previewMedicalCardImage();

}

onSelectedDoctorPhotoFile(event: any){
  this.selectedDoctorPhotoFile = event.target.files[0];
  this.previewDoctorImage();
}

previewMedicalCardImage(){
  const reader = new FileReader();
  reader.onload = ()=> {
    this.medicalCardPreview = reader.result;
  }

  reader.readAsDataURL(this.selectedMedicalCardFile!);

}

previewDoctorImage(){
  const reader = new FileReader();
  reader.onload = ()=> {
    this.doctorPhotoPreview = reader.result;
  }

  reader.readAsDataURL(this.selectedDoctorPhotoFile!);

}


  ngOnInit(): void {
  
    this.doctorSignupForm = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]],
      mobile: [null, [Validators.required]],
      specialityId: [null, [Validators.required]],
      briefIntroduction: [null, [Validators.required]],
      fees: [null, [Validators.required]],

    });

    this.getAllSpecialities();

  }
  getAllSpecialities() {
    this.authService.getAllSpecialities().subscribe(
      (response)=> {
        console.log("Specialities are=> " , response.payload);
       this.listOfSpecialities = response.payload;
      }
    )
    
  }


  async signUpAsDoctor(){

      const password = this.doctorSignupForm.get('password')?.value;
      const confirmPassword = this.doctorSignupForm.get('confirmPassword')?.value;
      if(password !== confirmPassword){
           this.snackBar.open("Password and confirm password don't match", "Close", {duration: 5000});
           return;
      }


    if(this.doctorSignupForm.valid){
       const firBaseToken = await this.notificationService.getOrRequestToken();
       console.log("Revcieved FCM ", firBaseToken);
       const formData: FormData = new FormData();

       console.log(this.doctorSignupForm.value);
       formData.append('medicalCardFile', this.selectedMedicalCardFile!);
       formData.append('doctorPhotoFile', this.selectedDoctorPhotoFile!);
       
       Object.keys(this.doctorSignupForm.controls).forEach(key=> {
         formData.append(key, this.doctorSignupForm.get(key)?.value);
       });

       //add notification token to the form data
       if (firBaseToken){
        formData.append('notificationToken', firBaseToken);
       }

       
       this.authService.signupAsDoctor(formData).subscribe(
        (response)=> {
            if(response.payload.id != null){
              this.snackBar.open(response.message, "Close", {duration: 5000});
              this.router.navigateByUrl('/signin');
            }
        },
        (error)=> {
           const errorMessage = error.error?.message || error.message || 'An un expected error occurs';
           this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
       )
      
       
    }
  }

  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;

  }

}
