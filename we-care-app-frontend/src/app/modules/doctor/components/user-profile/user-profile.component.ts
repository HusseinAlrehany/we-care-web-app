import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DoctorService } from '../../service/doctor.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserProfile } from '../../../../common-components/models/user-profile';
import { HttpErrorResponse } from '@angular/common/http';
import { UserProfileUpdateRequest } from '../../../../common-components/models/user-profile-update-request';
import { AppComponent } from '../../../../app.component';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordDialogComponent } from '../change-password-dialog/change-password-dialog.component';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.scss'
})
export class UserProfileComponent implements OnInit{

loading: boolean = false;
isEditable: boolean = false;

userProfileForm!: FormGroup;
userProfile!: UserProfile | null;

profileUpdateRequest!: UserProfileUpdateRequest;

existingDoctorImage?: string | null = null;
existingCardImage?: string | null = null;

doctorPhotoPreview?: string | ArrayBuffer | null;
medicalCardPreview?: string | ArrayBuffer | null;

selectedDoctorPhotoFile?: File | null;
selectedMedicalCardFile?: File | null;

cardChanged: boolean = false;
doctorImgChanged: boolean = false;



 constructor(private doctorService: DoctorService,
             private router: Router,
             private snackBar: MatSnackBar,
             private formBuilder: FormBuilder,
             private dialoge: MatDialog
 ){
  this.userProfileForm = this.formBuilder.group({
    password: [{value: '', disabled: true} , Validators.required],
    briefIntroduction: [{value: '', disabled: true}, Validators.required],
    fees: [{value: '', disabled: true}, Validators.required]
  });

 }

  ngOnInit(): void {
     this.getUserProfileById();
  }
  getUserProfileById() {
     this.doctorService.getUserProfileById().subscribe({
       next: (res)=> {
          console.log(res.payload);

          this.userProfile = res.payload;
          if(this.userProfile){
            this.userProfileForm.patchValue(this.userProfile);
            const API_BASE_URL = 'http://localhost:8080';
            //this time stamp is for bypassing the browser cache with img URL versioning with time stamp
            //since we fetch the url , when the img updated the url did not changed so the browser keep showing the cached url
            //adding time stamp force the browser cache to refetch the img url again
            const timeStamp = new Date().getTime();

            this.existingDoctorImage = `${API_BASE_URL}${this.userProfile.photoUrl}?t=${timeStamp}`;
            this.doctorPhotoPreview = this.existingDoctorImage;

            this.existingCardImage = `${API_BASE_URL}${this.userProfile.medicalCardUrl}?t=${timeStamp}`;
            this.medicalCardPreview = this.existingCardImage;

          }else {
            this.snackBar.open("No Profile Data Recieved", "Close", {duration: 3000});
          }
          
       },
       error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
        }
       }
     })
  }

  onDoctorFileSelected(event: any) {
   const file = event.target.files[0];
   if(file){
    this.selectedDoctorPhotoFile = file;
    this.previewDoctorImg();
    this.doctorImgChanged = true;
    this.existingDoctorImage = null;
   } else {
    this.selectedDoctorPhotoFile = null;
    this.doctorImgChanged = false;
    this.doctorPhotoPreview = this.existingDoctorImage;
   }

}
onCardFileSelected(event: any) {

  const file = event.target.files[0];
  if(file){
    this.selectedMedicalCardFile = file;
    this.previewMedicalCardImg();
    this.cardChanged = true;
    this.existingCardImage = null;
  }
  else {
    this.selectedMedicalCardFile = null;
    this.cardChanged = false;
    this.medicalCardPreview = this.existingCardImage;
  }

}

previewDoctorImg(){
  const reader = new FileReader();
  reader.onload = ()=> {
    this.doctorPhotoPreview = reader.result;
  }

  reader.readAsDataURL(this.selectedDoctorPhotoFile!);
}

previewMedicalCardImg(){
  const reader = new FileReader();
  reader.onload = ()=> {
    this.medicalCardPreview = reader.result;
  }

  reader.readAsDataURL(this.selectedMedicalCardFile!);
}

toggleEditing(): void {
   this.isEditable = !this.isEditable;

  //clicking on edit activate the fields
  if(this.isEditable){
      Object.keys(this.userProfileForm.controls).forEach(field=> {
     this.userProfileForm.get(field)?.enable();
     });
  }
  //cancel will deactivate the fields again
  else {
      Object.keys(this.userProfileForm.controls).forEach(field=> {
     this.userProfileForm.get(field)?.disable();
     });
  }
  
}

updateUserProfile() {

 const formData = new FormData();
 if(this.selectedDoctorPhotoFile){
  formData.append('doctorImgFile', this.selectedDoctorPhotoFile);
 }

 if(this.selectedMedicalCardFile){
  formData.append('medicalCardFile', this.selectedMedicalCardFile);
 }

 Object.keys(this.userProfileForm.controls).forEach(key=> {
    formData.append(key, this.userProfileForm.get(key)?.value);
 });

  this.doctorService.updateUserProfile(formData).subscribe({
    next: (res)=> {
      this.snackBar.open(res.message, "Close", {duration: 3000});
      this.router.navigateByUrl('/doctor/we_care_doctors');
       
    },
    error: (err)=> {
        const errorMessage = err.err.message || err.errorMessage || 'an unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 3000});
    }
  })
}

openChangePasswordDialog():void {
  this.dialoge.open(ChangePasswordDialogComponent, {
    width: '400px',
    disableClose: true
  });
}




}
