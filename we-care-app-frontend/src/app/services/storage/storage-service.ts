import { Injectable } from '@angular/core';
import { StoredUser } from '../../common-components/models/stored-user';

const USER = 'user' ;

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  
   isSessionStorageAvailable(): boolean{
    return (
      typeof window !== 'undefined' &&
      typeof window.sessionStorage !== 'undefined'
    );
  }

   saveUser(user: StoredUser): void {
    if(this.isSessionStorageAvailable()){
      sessionStorage.removeItem(USER);
      sessionStorage.setItem(USER, JSON.stringify(user));
    }
  }

   getUser(): StoredUser | null{
    if(this.isSessionStorageAvailable()){
      return JSON.parse(sessionStorage.getItem(USER)!);
    }
    return null;
  }

   getUserRole(): string{
    
    return this.getUser()?.payload?.userRole || '';
  }

   getUserId(): string {
    return this.getUser()?.payload?.userId || '';
  }

   isAdminLoggedIn(): boolean{
    return this.getUserRole() === 'ADMIN';
  }

   isDoctorLoggedIn(): boolean{
    return this.getUserRole() === 'DOCTOR';
  }
   isPatientLoggedIn(): boolean {
    return this.getUserRole() === 'PATIENT';
  }

   clearUserData(): void{
    if(this.isSessionStorageAvailable()){
      sessionStorage.removeItem(USER);
    }
  }



}
