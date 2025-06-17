import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllClinicsComponent } from './all-clinics.component';

describe('AllClinicsComponent', () => {
  let component: AllClinicsComponent;
  let fixture: ComponentFixture<AllClinicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllClinicsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllClinicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
