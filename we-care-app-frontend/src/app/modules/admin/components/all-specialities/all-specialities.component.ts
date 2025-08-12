import { Component, OnInit } from '@angular/core';
import { SpecialitiesDTO } from '../../../../common-components/models/specialities-dto';
import { AdminService } from '../../service/admin-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../Material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { SharedService } from '../../../../common-components/shared-service';

@Component({
  selector: 'app-all-specialities',
  standalone: true,
  imports: [CommonModule, MaterialModule, ReactiveFormsModule, RouterLink],
  templateUrl: './all-specialities.component.html',
  styleUrl: './all-specialities.component.scss'
})
export class AllSpecialitiesComponent implements OnInit{
    
  listOfSpecialities: SpecialitiesDTO [] = [];

  constructor(private adminService: AdminService, 
              private snackBar: MatSnackBar,
              private sharedService: SharedService 
   ){}


  ngOnInit(): void {
    this.getAllSpecialities();
  }
  getAllSpecialities() {
    this.listOfSpecialities = [];
     this.sharedService.getAllSpecialities().subscribe(
      (response)=> {
         console.log(response);
         this.listOfSpecialities = response.payload;
      },
      (error)=> {
        const errorMessage = error.error?.error || error.message || 'An unexpected error occured';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
     )
  }

  deleteSpeciality(id?: number){
    this.adminService.deleteSpecialityById(id!).subscribe(
       (response)=> {
           this.snackBar.open(response.message, "Close", {duration: 5000});
           this.getAllSpecialities();
       },
       (error)=> {
          const errorMessage = error.error?.message || error.message || 'An un expected Error occured';
          this.snackBar.open(errorMessage, "Close", {duration: 5000});
       }
    )
  }



}
