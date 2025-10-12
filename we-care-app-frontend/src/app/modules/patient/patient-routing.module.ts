import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewDoctorsComponent } from './components/view-doctors/view-doctors.component';
import { BookedVisitsComponent } from './components/booked-visits/booked-visits.component';
import { VisitsHistoryComponent } from './components/visits-history/visits-history.component';
import { VisitBookingComponent } from './components/visit-booking/visit-booking.component';
import { AddReviewComponent } from './components/add-review/add-review.component';

const routes: Routes = [
  {path: 'view_doctors', component: ViewDoctorsComponent},
  {path: 'my_visits', component: BookedVisitsComponent},
  {path: 'visits_history', component: VisitsHistoryComponent},
  {path: 'visit_booking', component: VisitBookingComponent},
  {path: 'add-review/:id', component: AddReviewComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
