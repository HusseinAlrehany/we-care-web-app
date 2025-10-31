import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../Material.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, formatDate } from '@angular/common';
import { SharedService } from '../shared-service';
import { SpecialitiesDTO } from '../models/specialities-dto';
import { StateDTO } from '../models/state-dto';
import { CityDTO } from '../models/city-dto';
import { DoctorClinicScheduleDTO } from '../models/doctor-clinic-schedule-dto';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DoctorFilter } from '../models/doctor-filter';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ScheduleDTO } from '../models/schedule-dto';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-shared-view-doctors',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './shared-view-doctors.component.html',
  styleUrl: './shared-view-doctors.component.scss'
})
export class SharedViewDoctorsComponent implements OnInit{
  specialities: SpecialitiesDTO [] = [];
  states: StateDTO [] = [];
  cities: CityDTO [] = [];
  doctorClinicSchedules: DoctorClinicScheduleDTO [] = [];

  pageNumber: number = 1;
  pageSize:number = 10;
  totalElements: number = 0;
  currentPage: number = 0;
  pageSizeOptions: number[] = [2, 4, 8 , 10];


  filterForm!: FormGroup;
  isLoading: boolean = false;
  hasFilters: boolean = false;
  private MIN_LOADING_TIME = 500;
  loadingStartTime: any;
  selectedSlot:any = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;


constructor(private sharedService: SharedService,
            private formBuilder: FormBuilder,
            private snackBar: MatSnackBar,
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
    this.loadDoctorClinicScheduleDTO(this.pageNumber, this.pageSize);
    this.loadSpecialities();
    this.loadStates();

    //dynamically load corresponding cities according to selected stateID
    this.filterForm.get('state')?.valueChanges.subscribe(
      (selectedState)=> {
        this.filterForm.get('city')?.reset();
        this.cities = [];

        if(selectedState?.id){
          this.loadCitiesByStateId(selectedState.id);
        }
      }
    );
    
  }

 applyFilters(): void{
  this.pageNumber = 1;
  this.currentPage = 0;

  this.hasFilters = this.hasActiveFilters();

  if(this.hasFilters){
    this.loadFilteredDoctors();
  }else {
    this.loadDoctorClinicScheduleDTO(this.pageNumber, this.pageSize);
  }

 }

 //extract filters from filter form and assign it in DoctorFilter Object.
 extractFilters(): DoctorFilter{
  const formValue = this.filterForm.value;

  return {
    doctorName: formValue.doctorName || undefined,
    specialityName: formValue.speciality?.name || undefined,
    stateName: formValue.state?.stateName || undefined,
    cityName: formValue.city?.cityName || undefined

  }
 } 

 clearFilters(){
  this.filterForm.reset();
 }

 loadFilteredDoctors(){
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
    error: (error: HttpErrorResponse)=> {
      if(error.status === 404 && error.error){
        this.snackBar.open(error.error.message, "Close", {duration: 3000});
        this.setLoadingState(false);
      }else {
        this.snackBar.open("An unexpected error occured", "Close", {duration: 3000});
      }
    }
  })
 }


  loadDoctorClinicScheduleDTO(pageNumber: number, pageSize: number) {
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
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
          this.setLoadingState(false);
        }
      }
    });
  }

  onPageChange(event: PageEvent){
    this.pageNumber =  event.pageIndex + 1;
    this.pageSize = event.pageSize;

    if(this.hasFilters){
      //update the page number before loading the filtered doctors.
      this.pageNumber = event.pageIndex + 1;
      this.loadFilteredDoctors();
    } else {
      this.loadDoctorClinicScheduleDTO(this.pageNumber, this.pageSize);
    }
  }
  

 hasActiveFilters(): boolean {
  const formValue = this.filterForm.value;

  return !!formValue.speciality || !!formValue.state || !!formValue.city || !!formValue.doctorName;
 }

  loadCitiesByStateId(stateId: number) {
   
    this.sharedService.getCitiesByStateId(stateId).subscribe({
       next: (res)=> {
        this.cities = res.payload;
       },
       error: (error: HttpErrorResponse)=> {
        if(error.status === 404 && error.error){
          this.snackBar.open(error.error.message, "Close", {duration: 3000});
        }
       }
    })
  }


  loadSpecialities() {
     this.sharedService.getAllSpecialities().subscribe({
      next: (res)=> {
         this.specialities = res.payload;
      },
      error: (error: HttpErrorResponse)=> {
          if(error.status === 404 && error.error){
            this.snackBar.open(error.error.message, "Close", {duration: 3000});
          }
      }
     })
  }

loadStates() {
  this.sharedService.getAllStates().subscribe({
    next: (res)=> {
      this.states = res.payload;
    },
    error: (error: HttpErrorResponse)=> {
      if(error.status === 404 && error.error){
        this.snackBar.open(error.message, "Close", {duration:3000});
      }
    }
  })
}



 //set min time for loading spinner
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


   this.router.navigate(['/guest_visit_booking'], {
    state: {
      doctorId: doctor.doctorId,
      clinicId: doctor.clinicId,
      scheduleId: selectedSlot.id
    }
  });
 
}


  formatTimeToAMPM(timeString: string): string {
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(+hours, +minutes);
  
    return formatDate(date, 'hh:mm a', 'en-US');
  }
 

}





