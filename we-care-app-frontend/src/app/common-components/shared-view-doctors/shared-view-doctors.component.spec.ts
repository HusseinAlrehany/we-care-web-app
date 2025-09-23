import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedViewDoctorsComponent } from './shared-view-doctors.component';

describe('SharedViewDoctorsComponent', () => {
  let component: SharedViewDoctorsComponent;
  let fixture: ComponentFixture<SharedViewDoctorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SharedViewDoctorsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SharedViewDoctorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
