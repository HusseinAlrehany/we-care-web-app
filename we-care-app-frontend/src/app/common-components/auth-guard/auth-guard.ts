import { inject } from "@angular/core";
import { CanActivateFn } from "@angular/router";
import { StorageService } from "../../services/storage/storage-service";

export const authGuard: CanActivateFn = () => {

  const storageService = inject(StorageService);
  
  return storageService.isAdminLoggedIn () || 
         storageService.isDoctorLoggedIn ()||
         storageService.isPatientLoggedIn(); 
};
