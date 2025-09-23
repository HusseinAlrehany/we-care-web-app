import { Component, ElementRef, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule, formatDate } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DoctorClinicScheduleDTO } from '../../../../common-components/models/doctor-clinic-schedule-dto';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ScheduleDTO } from '../../../../common-components/models/schedule-dto';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedService } from '../../../../common-components/shared-service';
import { StateDTO } from '../../../../common-components/models/state-dto';
import { CityDTO } from '../../../../common-components/models/city-dto';
import { SpecialitiesDTO } from '../../../../common-components/models/specialities-dto';
import { DoctorFilter } from '../../../../common-components/models/doctor-filter';
import { Router} from '@angular/router';
@Component ({
  selector: 'app-view-doctors',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, NgbCarouselModule],
  templateUrl: './view-doctors.component.html',
  styleUrl: './view-doctors.component.scss'
})
export class ViewDoctorsComponent implements OnInit{

specialities: SpecialitiesDTO[] = [];
states: StateDTO[] = [];
cities: CityDTO[] = [];
filterForm!: FormGroup;
doctorClinicSchedules: DoctorClinicScheduleDTO [] = [];

pageNumber:number = 1;
pageSize: number = 10;
totalElements: number = 0;
currentPage: number = 0;
pageSizeOptions: number[] = [1,2,4,6,8];

selectedSlot:any = null;
hasFilters: boolean = false;
isLoading: boolean = false;
private MIN_LOADING_TIME = 500; // in milliseconds
loadingStartTime: any ;

@ViewChild(MatPaginator) paginator!: MatPaginator;


constructor(private formBuilder: FormBuilder,
            private snackBar: MatSnackBar,
            private sharedService: SharedService,
            private router: Router
            
){

  this.filterForm = this.formBuilder.group({
    speciality: [''],
    state: [''],
    city: [''],
    doctorName: ['']
  });

}
  ngOnInit(): void {
    
    this.getDoctorClinicSchedulePage(this.pageNumber, this.pageSize);
    this.getAllStates();
    this.getAllSpecialities();

    //dynamically load corresponding cities realated to selected state ID
    this.filterForm.get('state')?.valueChanges.subscribe(
      (selectedState)=> {
        this.filterForm.get('city')?.reset();
        this.cities = [];

        if(selectedState?.id){
          this.getCitiesByStateId(selectedState.id);
        }
      }
    );

   
  }

  //loading spinner logic 
  //set a min time for spinner
  setLoadingState(loading: boolean): void {
    if(loading){
        this.isLoading = loading;
        this.loadingStartTime = Date.now();
    }else {
      const elapsed = Date.now() - this.loadingStartTime;
      const remaining = this.MIN_LOADING_TIME - elapsed;

      if(remaining > 0) {
        setTimeout(()=> {
          this.isLoading = false;          
        }, remaining);
      } else {
        this.isLoading = false;
      }
    }
  }
  
 applyFilters(): void {
   this.pageNumber = 1;
   this.currentPage = 0;
   
  this.hasFilters = this.hasActiveFilters();

  if(this.hasFilters){
    this.loadFilteredDoctors();
  } else {
    this.getDoctorClinicSchedulePage(this.pageNumber, this.pageSize);
  }

 }

 hasActiveFilters(): boolean {
  //assign filter form value in object (formValue)
  const formValue = this.filterForm.value;
  //every !! converts every value to a boolean 
  //if there is value like !!'cardiology' it evaluted to true if not it evaluted to false 
  return !!formValue.speciality || !!formValue.state ||
           !!formValue.city || !!formValue.doctorName;
 }

 loadFilteredDoctors(): void {
  this.setLoadingState(true);
    this.doctorClinicSchedules = [];
    const filters = this.extractFilters();

   this.sharedService.filterDoctorsClinicSchedule(this.pageNumber, this.pageSize, filters).subscribe({
    next: (res)=> {
      this.doctorClinicSchedules = res.payload.content;
      this.totalElements = res.payload.totalElements;
      this.currentPage = res.payload.number;
      this.setLoadingState(false);
    },
    error: (error:HttpErrorResponse)=> {
      this.isLoading = false;
      if(error.status === 404 && error.error){
        this.snackBar.open(error.error.message, "Close", {duration:3000});
      }else {
        this.snackBar.open("An unexpected error occured", "Close", {duration: 3000});
      }
    }
   });
 }

 //extract filters from the filter form and return it as DoctorFilter object
 extractFilters(): DoctorFilter {
  const formValue = this.filterForm.value;
  return {
      doctorName: formValue.doctorName || undefined,
      specialityName: formValue.speciality?.name || undefined,
      stateName: formValue.state?.stateName || undefined,
      cityName: formValue.city?.cityName || undefined
  };
 }

 clearFilters(){
  this.filterForm.reset();
   //this commented line if we want to load all doctors page after clearing filters
  //this.getDoctorClinicSchedulePage(this.pageNumber, this.pageSize);
 }

getCitiesByStateId(stateId: number) {
  this.sharedService.getCitiesByStateId(stateId).subscribe({
    next: (res)=> {
      this.cities = res.payload;
    },
    error: (error: HttpErrorResponse)=> {
      if(error.status === 404 && error.error){
        this.snackBar.open(error.error.message, "Close", {duration: 3000});
      }
    }
  });

}

  getAllSpecialities() {
     this.sharedService.getAllSpecialities().subscribe({
      next: (res)=> {
        console.log("Specialities are" , res.payload);
        this.specialities = res.payload;

      },
      error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.message, "Close", {duration: 3000});
        }
      }
     });
  }
  getAllStates() {
    this.sharedService.getAllStates().subscribe({
      next: (res)=> {
          this.states = res.payload;
      },
      error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
        }
      }
    });
  }


  onPageChange(event: PageEvent): void {
     this.pageNumber = event.pageIndex + 1;
     this.pageSize = event.pageSize;

     if(this.hasFilters){
      //updating the page number before loading the filtered doctors
       this.pageNumber = event.pageIndex + 1;
       this.loadFilteredDoctors();

     }else {
        this.getDoctorClinicSchedulePage(this.pageNumber, this.pageSize);
     }

    
  }
  getDoctorClinicSchedulePage(pageNumber: number, pageSize: number) {
    
    this.setLoadingState(true);
    this.sharedService.getDoctorClinicSchedulePage(pageNumber, pageSize).subscribe({
      next: (res)=> {
        this.doctorClinicSchedules = res.payload.content;
        this.totalElements = res.payload.totalElements;
        this.currentPage = res.payload.number;
        this.setLoadingState(false);
      },
      error: (error: HttpErrorResponse)=> {
          if(error.status === 404 && error.error){
            this.snackBar.open(error.message, "Close", {duration: 3000});
            this.setLoadingState(false);
          }
      }
    })
  }



//start of schedules crousal
// doctor.schedules: ScheduleDTO[]
getGroupedSchedules(schedules: ScheduleDTO[]) {
  const grouped = new Map<string, ScheduleDTO[]>();

  for (let sched of schedules) {
    const date = sched.date;
    if (!grouped.has(date)) {
      grouped.set(date, []);
    }
    grouped.get(date)?.push(sched);
  }

  return Array.from(grouped.entries()).map(([date, slots]) => ({
    date,
    slots
  }));
}


//the start and end time is of type LocalTime in backend
//but we recieve it as string in typescript interface 
//so we need to manually convert it to Date object
//then format it using date pipe of angular(as PM or AM) because date pipe works with Date objects not string
formatTimeToAMPM(timeString: string): string {
  const [hours, minutes] = timeString.split(':');
  const date = new Date();
  date.setHours(+hours, +minutes);

  return formatDate(date, 'hh:mm a', 'en-US');
}

//to mark a selected appointment as selected
onSlotClick(slot: any, item:any): void{
   this.selectedSlot = slot;
}


//this method is used to capture (doctorId, clinicId, scheduleId)
//to be used in visit booking component as hidden fields
//here we send doctor, selectSlot from the html
bookNow(doctor: any, selectedSlot: any){

  //force the user to first select a time
  if(!selectedSlot){
    this.snackBar.open("Please select a time first", "Close", {
      duration: 3000,
     
    });

    //exit from the function to avoid throwing error
    //without return; it will show the error message plus the error itself in the console
    return;
  }


   this.router.navigate(['/patient/visit_booking'], {
    state: {
      doctorId: doctor.doctorId,
      clinicId: doctor.clinicId,
      scheduleId: selectedSlot.id
    }
  });
 
}



}
