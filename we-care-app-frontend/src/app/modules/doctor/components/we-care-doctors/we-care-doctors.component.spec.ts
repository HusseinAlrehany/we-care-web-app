import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeCareDoctorsComponent } from './we-care-doctors.component';

describe('WeCareDoctorsComponent', () => {
  let component: WeCareDoctorsComponent;
  let fixture: ComponentFixture<WeCareDoctorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WeCareDoctorsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WeCareDoctorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
