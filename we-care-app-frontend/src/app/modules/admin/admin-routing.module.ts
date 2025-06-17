import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllDoctorsComponent } from './components/all-doctors/all-doctors.component';
import { AddSpecialityComponent } from './components/add-speciality/add-speciality.component';
import { AddDoctorComponent } from './components/add-doctor/add-doctor.component';
import { BookedVisitsComponent } from './components/booked-visits/booked-visits.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { ReportsComponent } from './components/reports/reports.component';
import { AllSpecialitiesComponent } from './components/all-specialities/all-specialities.component';
import { AddClinicComponent } from './components/add-clinic/add-clinic.component';
import { AllClinicsComponent } from './components/all-clinics/all-clinics.component';
import { SpecialityDetailsComponent } from './components/speciality-details/speciality-details.component';

const routes: Routes = [
  {path: 'view_doctors', component: AllDoctorsComponent},
  {path: 'add_speciality', component: AddSpecialityComponent},
  {path: 'add_doctor', component: AddDoctorComponent},
  {path: 'add_clinic', component: AddClinicComponent},
  {path: 'booked_visits', component: BookedVisitsComponent},
  {path: 'visits_statistics', component: StatisticsComponent},
  {path: 'reports', component: ReportsComponent},
  {path: 'all_specialities', component: AllSpecialitiesComponent},
  {path: 'all_clinics', component: AllClinicsComponent},
  {path: 'speciality-details/:id', component: SpecialityDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
