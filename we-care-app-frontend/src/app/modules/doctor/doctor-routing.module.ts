import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WeCareDoctorsComponent } from './components/we-care-doctors/we-care-doctors.component';
import { AddScheduleComponent } from './components/add-schedule/add-schedule.component';
import { ViewSchedulesComponent } from './components/view-schedules/view-schedules.component';
import { EditScheduleComponent } from './components/edit-schedule/edit-schedule.component';
import { AddClinicComponent } from './components/add-clinic/add-clinic.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { TodayBookedVisitsComponent } from './components/today-booked-visits/today-booked-visits.component';
import { PreviousVisitsComponent } from './components/previous-visits/previous-visits.component';

const routes: Routes = [
  {path: 'we_care_doctors', component: WeCareDoctorsComponent},
  {path: 'todays_visits', component: TodayBookedVisitsComponent},
  {path: 'past_visits', component: PreviousVisitsComponent},
  {path: 'add_schedule', component: AddScheduleComponent},
  {path: 'add_clinic', component: AddClinicComponent},
  {path: 'view_schedules', component: ViewSchedulesComponent},
  {path: 'edit-schedule/:id', component: EditScheduleComponent},
  {path: 'profile', component: UserProfileComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
