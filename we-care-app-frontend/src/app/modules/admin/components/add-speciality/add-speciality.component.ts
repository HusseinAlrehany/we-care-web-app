import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminService} from '../../service/admin-service';
import { SpecialitiesDTO } from '../../../../common-components/models/specialities-dto';

@Component({
  selector: 'app-add-speciality',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-speciality.component.html',
  styleUrl: './add-speciality.component.scss'
})
export class AddSpecialityComponent implements OnInit{

  addSpecialityForm!: FormGroup;
  specialityDTO!: SpecialitiesDTO;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar,
              private adminService: AdminService
  ){}
  ngOnInit(): void {

    this.addSpecialityForm = this.formBuilder.group({
      name: [null, [Validators.required]]
    })
  }

  addSpeciality(){
    this.specialityDTO = this.addSpecialityForm.value;
    if(this.addSpecialityForm.valid){
      this.adminService.addSpeciality(this.specialityDTO).subscribe(
        (response)=> {
          if(response.payload.id != null){
            this.snackBar.open(response.message, "Close", {duration: 5000});
            this.router.navigateByUrl('/admin/all_specialities');
          }
        },
        (error)=> {
          const errorMessage = error.error?.message || error.message || 'An un expected Error Occured';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
        }
      )
    }
  }

}
