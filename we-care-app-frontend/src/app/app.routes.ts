import { Routes } from '@angular/router';
import { SignInComponent } from './common-components/sign-in/sign-in.component';
import { PatientSignupComponent } from './common-components/patient-signup/patient-signup.component';
import { DoctorSignupComponent } from './common-components/doctor-signup/doctor-signup.component';
import { authGuard } from './common-components/auth-guard/auth-guard';
import { AiModelComponent } from './common-components/ai-model/ai-model.component';
import { SharedViewDoctorsComponent } from './common-components/shared-view-doctors/shared-view-doctors.component';
import { PharmacyComponent } from './common-components/pharmacy/pharmacy.component';
import { GuestVisitBookingComponent } from './common-components/guest-visit-booking/guest-visit-booking.component';
import { DoctorDetailsComponent } from './common-components/doctor-details/doctor-details.component';
import { DoctorReviewsComponent } from './common-components/doctor-reviews/doctor-reviews.component';

export const routes: Routes = [
    {path: 'patient_signup', component: PatientSignupComponent},
    {path: 'doctor_signup', component: DoctorSignupComponent},
    {path: 'signin', component: SignInComponent},
    {path: 'we_care_AI', component: AiModelComponent},
    {path: 'view_doctors', component: SharedViewDoctorsComponent},
    {path: 'pharmacy', component: PharmacyComponent},
    {path: 'guest_visit_booking', component: GuestVisitBookingComponent},
    {path: 'details/:id', component: DoctorDetailsComponent},
    {path: 'reviews/:id', component: DoctorReviewsComponent},

    {path: 'admin', loadChildren: ()=> import("./modules/admin/admin.module")
        .then(e=>e.AdminModule), canActivate: [authGuard]
    },
    {path: 'doctor', loadChildren: ()=> import("./modules/doctor/doctor.module")
        .then(e=>e.DoctorModule), canActivate: [authGuard]
    },
    {path: 'patient', loadChildren: ()=> import("./modules/patient/patient.module")
        .then(e=>e.PatientModule), canActivate: [authGuard]
    }
];
