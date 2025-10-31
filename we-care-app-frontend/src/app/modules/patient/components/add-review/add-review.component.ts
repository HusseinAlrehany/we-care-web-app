import { Component } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm, ReactiveFormsModule } from '@angular/forms';
import { PatientService } from '../../service/patient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReviewDTORequest } from '../../../../common-components/models/review-dtorequest';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-review',
  standalone: true, 
  imports: [MaterialModule, CommonModule, FormsModule],
  templateUrl: './add-review.component.html',
  styleUrl: './add-review.component.scss'
})
export class AddReviewComponent {
  doctorId: number = this.activeRoute.snapshot.params['id'];
  rating: number = 0;
  comment: string = '';
  stars: number [] = [1, 2, 3, 4, 5];

constructor(private patientService: PatientService,
            private activeRoute: ActivatedRoute,
            private snackBar: MatSnackBar,
            private router: Router
){}


setRating(value: number){
   this.rating = value;
}
  
 submitReview(form: NgForm){
  const review: ReviewDTORequest = {
     doctorId: this.doctorId,
     comment: this.comment,
     rating: this.rating
  };

  if(form.invalid || this.rating === 0){
      console.warn('Form is invalid or rating not selected');
      return;
  }

  this.patientService.addReview(review).subscribe({
    next: (res)=> {
       
         this.snackBar.open(res.message, "Close", {duration: 3000});
         this.router.navigate(['reviews/', this.doctorId]);
    },
    error: (error: HttpErrorResponse)=> {
      if(error.status === 404 && error.error){
        this.snackBar.open(error.error.message, "Close", {duration: 3000});
      }
    }
  })
 }

}
