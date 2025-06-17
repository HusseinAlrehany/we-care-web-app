import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WeCareDoctorsComponent } from './components/we-care-doctors/we-care-doctors.component';

const routes: Routes = [
  {path: 'we_care_doctors', component: WeCareDoctorsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DoctorRoutingModule { }
