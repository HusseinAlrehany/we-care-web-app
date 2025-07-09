import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from '../../service/admin-service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SpecialitiesDTO } from '../../../../common-components/models/specialities-dto';

@Component({
  selector: 'app-update-doctor',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './update-doctor.component.html',
  styleUrl: './update-doctor.component.scss'
})
export class UpdateDoctorComponent implements OnInit{

  loading: boolean = false;
  updateForm!: FormGroup;
  specialities: SpecialitiesDTO[] = [];

  selectedMedicalCardFile?: File | null;
  selectedDoctorPhotoFile?: File | null;
  
  
  medicalCardPreview?: string | ArrayBuffer | null;
  doctorPhotoPreview?: string | ArrayBuffer | null;

  existingDoctorImage: string | null = null;
  existingCardImage: string | null = null;

  cardChanged = false;
  doctorImgChanged = false;



  doctorId:number = this.activatedRoute.snapshot.params['id'];

  constructor(private adminService:AdminService,
              private formBuilder: FormBuilder,
              private router:Router,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar
  ){}

onDoctorFileSelected(event: any){
       //this.selectedFile = event.target.files[0];
       const file = event.target.files[0];
       if(file){ //if the user select a file
        this.selectedDoctorPhotoFile = file; //stores the selected file
        this.previewDoctorImage(); //preview it to the user
        this.doctorImgChanged = true; //the doctor img is changed = true
        this.existingDoctorImage = null; //remove the old image preview
       }
       else { //if the user not select the file
        this.selectedDoctorPhotoFile = null;
        this.doctorImgChanged = false;
        this.doctorPhotoPreview = this.existingDoctorImage;
       }
       
    }
  previewDoctorImage() {
    const reader = new FileReader();
    reader.onload = ()=> {
      this.doctorPhotoPreview = reader.result;
    }

    reader.readAsDataURL(this.selectedDoctorPhotoFile!);
  }


    onCardFileSelected(event: any){
       //this.selectedFile = event.target.files[0];
       const file = event.target.files[0];
       if(file){
        this.selectedMedicalCardFile = file;
        this.previewCardImage();
        this.cardChanged = true;
        this.existingCardImage = null;
       }
       else {
        this.selectedMedicalCardFile = null;
        this.cardChanged = false;
        this.medicalCardPreview = this.existingCardImage;
       }
       
    }
  previewCardImage() {
    const reader = new FileReader();
    reader.onload = ()=> {
      this.medicalCardPreview = reader.result;
    }
    reader.readAsDataURL(this.selectedMedicalCardFile!);
  }


  ngOnInit(): void {
    
    this.updateForm = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      briefIntroduction: ['', [Validators.required]],
      fees: ['', [Validators.required]],
      specialityId: ['', [Validators.required]]
    });

     this.getAllSpecialities();
     this.getDoctorById(this.doctorId);
  }
  getDoctorById(doctorId: number) {
    this.adminService.getDoctorById(doctorId).subscribe(
      (res)=> {
        this.updateForm.patchValue(res.payload);


      const API_BASE_URL = 'http://localhost:8080';

      this.existingDoctorImage = `${API_BASE_URL}${res.payload.doctorImageURL}`;
      this.doctorPhotoPreview = this.existingDoctorImage;

      this.existingCardImage = `${API_BASE_URL}${res.payload.medicalCardURL}`;
      this.medicalCardPreview = this.existingCardImage;
      }
    )
  }
  getAllSpecialities() {
    this.adminService.getAllSpecialities().subscribe(
      (res)=> {
        console.log("SPECIALITIES" , res);
        this.specialities = res.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

  updateDoctor(){
    if(this.updateForm.valid){
      this.loading = true;
      const formData = new FormData();
      console.log(this.updateForm.value);

      if(this.selectedDoctorPhotoFile){
          formData.append('doctorImgFile', this.selectedDoctorPhotoFile!);
      }

      if(this.selectedMedicalCardFile){
         formData.append('medicalCardFile', this.selectedMedicalCardFile!);
      }


      Object.keys(this.updateForm.controls).forEach(key=> {
        formData.append(key, this.updateForm.get(key)?.value);
      });

      this.adminService.updateDoctor(formData, this.doctorId).subscribe(
        (res)=> {
          if(res.payload.id != null){
            this.snackBar.open(res.message, "Close", {duration: 5000});
            this.router.navigateByUrl('/admin/view_doctors');
          }
        },
        (error)=> {
          const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occured';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
      )

              
    }
  }





 

}
