import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../service/admin-service';
import { DoctorDTO } from '../../../../common-components/models/doctor-dto';
import { MaterialModule } from '../../../../Material.module';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SpecialitiesDTO } from '../../../../common-components/models/specialities-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CityDTO } from '../../../../common-components/models/city-dto';
import { StateDTO } from '../../../../common-components/models/state-dto';
import { SharedService } from '../../../../common-components/shared-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-all-doctors',
  standalone: true,
  imports: [MaterialModule, CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './all-doctors.component.html',
  styleUrl: './all-doctors.component.scss'
})
export class AllDoctorsComponent implements OnInit{

  listOfDoctors: DoctorDTO [] = [];

  specialities: SpecialitiesDTO [] = [];

  allStates: StateDTO[] = [];

  allCitiesByStateName: CityDTO[] = [];



  searchForm!: FormGroup;
  
  constructor(private adminService: AdminService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private sharedService: SharedService,
              private router: Router,
              private activatedRoute: ActivatedRoute
  ){

    this.searchForm = this.formBuilder.group({
      name: ['', ],
      speciality: [''],
      state: [''],
      city: ['']
    })
  }



  ngOnInit(): void {
    this.getAllDoctors();
    this.getAllStates();
    this.getAllSpecialities();

    //load the cities dynamically when the state changes
    this.searchForm.get('state')?.valueChanges.subscribe(
      (selectedState)=> {
        this.searchForm.get('city')?.reset();
        this.allCitiesByStateName = [];
        if(selectedState?.stateName){
          this.getCitiesByStateName(selectedState.stateName);

        }
      }
    );


    //listen to route changes and reload doctors list
    //scence angular not re initialize the component if you navigate to the same route, unless forcing it to do so
    this.sharedService.reloadDoctorsList$.subscribe(()=> {
      this.getAllDoctors();
    })

    
  }
  
  //to be cahnged to get cities by stateId
  getCitiesByStateName(selectedState: string) {
    this.sharedService.getCitiesByStateName(selectedState).subscribe(
      (response)=> {
        this.allCitiesByStateName = response.payload;
      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.message || 'An unexpected error occurs';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }
 
  getAllStates() {
    this.sharedService.getAllStates().subscribe(
      (response)=> {
        this.allStates = response.payload;
      }
    )
  }
  
  getAllDoctors() {
    this.listOfDoctors = [];
     this.adminService.getAllDoctors().subscribe(
      (res)=> {
         console.log(res);
         this.listOfDoctors = res.payload;

      },
      (error)=> {
         const errorMessage = error?.error?.message || error?.message || 'An unexpected error occured';
         this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
     )
  }

  getAllSpecialities() {
    this.adminService.getAllSpecialities().subscribe(
      (res)=> {
        this.specialities = res.payload;

      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.message || 'An unexpected Error occures';
        this.snackBar.open(errorMessage, "Close", {duration: 5000});
      }
    )
  }

  filterDoctorsBy(){
    let doctorName: string = this.searchForm.get('name')?.value || null;
    let specialityName: string = this.searchForm.get('speciality')?.value?.name || null;
    let stateName: string = this.searchForm.get('state')?.value?.stateName || null;
    let cityName: string = this.searchForm.get('city')?.value?.cityName || null;

    this.sharedService.filterDoctors(doctorName, specialityName,stateName, cityName).subscribe(
      (res)=> {
        this.listOfDoctors = [];
        this.listOfDoctors = res.payload;
        this.searchForm.reset();

      },
      (error)=> {
        const errorMessage = error?.error?.message || error?.errorMessage || 'An unexpected error occures';
        this.snackBar.open(errorMessage, "Close", {duration: 5000}); 
      }
                                     )
  }

  onDelete(id?: number){
    this.adminService.deleteDoctor(id!).subscribe(
      (response)=> {
        this.snackBar.open(response.message, "Close", {duration: 5000});
        this.getAllDoctors();
      },
      (error)=> {
        const errorMessage = error?.error?.message|| error?.message || 'An unexpected error occured';
         this.snackBar.open(errorMessage, "Close", {duration: 5000});
         this.getAllDoctors();
      }

    )
  }





}
