import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WeCareDoctorsComponent } from './components/we-care-doctors/we-care-doctors.component';
import { AddScheduleComponent } from './components/add-schedule/add-schedule.component';
import { ViewSchedulesComponent } from './components/view-schedules/view-schedules.component';
import { EditScheduleComponent } from './components/edit-schedule/edit-schedule.component';

const routes: Routes = [
  {path: 'we_care_doctors', component: WeCareDoctorsComponent},
  {path: 'add_schedule', component: AddScheduleComponent},
  {path: 'view_schedules', component: ViewSchedulesComponent},
  {path: 'edit-schedule/:id', component: EditScheduleComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
