import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterLinkActive, RouterModule, RouterOutlet } from '@angular/router';
import { MaterialModule } from './Material.module';
import { CommonModule } from '@angular/common';
import { StorageService } from './services/storage/storage-service';
import { AuthService } from './services/authentication/auth-service';
import { StoredUser } from './common-components/models/stored-user';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SharedService } from './common-components/shared-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MaterialModule, RouterLink, RouterLinkActive, CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{

  title = 'we-care-app';
  isHomePage = true;

  isAdminLoggedIn: boolean = this.storageService.isAdminLoggedIn();
  isDoctorLoggedIn: boolean =  this.storageService.isDoctorLoggedIn();
  isPatientLoggedIn: boolean =  this.storageService.isPatientLoggedIn();

  constructor(private router: Router, 
              private storageService: StorageService,
              private authService: AuthService,
              private sharedService: SharedService,
              private snackBar: MatSnackBar){

    this.router.events.subscribe(event =>{
      if(event instanceof NavigationEnd){
        this.isHomePage = event.url === '/';
      }});
  }

  ngOnInit(): void {
    this.router.events.subscribe(event=> {
      this.isAdminLoggedIn =  this.storageService.isAdminLoggedIn();
      this.isDoctorLoggedIn =  this.storageService.isDoctorLoggedIn();
      this.isPatientLoggedIn =  this.storageService.isPatientLoggedIn();
    })
  }

  signout(){
    this.authService.logout().subscribe(
      (res)=> {

        const user: StoredUser = {
          message: res.message,
          payload: null };
        
          this.snackBar.open(res.message, "Close", {duration: 5000});

        this.storageService.clearUserData();
        this.router.navigateByUrl('/signin');
      },
      (error)=> {
        const errorMessage = error.error?.message || error.message || 'An expected Error Occures';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
        this.storageService.clearUserData();
        this.router.navigateByUrl('/signin');
      }
    )
  }

  //call the function to reload all doctors when clicking on we care doctors link
  //to force angular to reload the component since angular not support automatic reloading of the component
  //when navigating on the same route
  navigateToAllDoctors(){
    this.router.navigate(['/admin/view_doctors']).then(()=> {
      this.sharedService.triggerReloadDoctorsList(); //triggering reload all doctors
    });
  }

}
