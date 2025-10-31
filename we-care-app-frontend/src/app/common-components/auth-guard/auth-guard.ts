import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { StorageService } from "../../services/storage/storage-service";
import { MatSnackBar } from "@angular/material/snack-bar";

export const authGuard: CanActivateFn = (route, state) => {

  const storageService = inject(StorageService);
  const router = inject(Router);
  const snackBar = inject(MatSnackBar);

  const loggedIn = 
        storageService.isAdminLoggedIn() ||
        storageService.isDoctorLoggedIn() ||
        storageService.isPatientLoggedIn();

  if(!loggedIn){
     snackBar.open("You Must Logged In To Access This Page", "Close", {
       duration: 3000,
       verticalPosition: "top",
       horizontalPosition: "center"
     });

    //enforce routing to signin page 
    router.navigate(["/signin"]);
    //in not logged in cancel navigation
    return false;
  }
  
  //if logged in allow access to the resource
  return true;

  
  /*return storageService.isAdminLoggedIn () || 
         storageService.isDoctorLoggedIn ()||
         storageService.isPatientLoggedIn(); */


};
