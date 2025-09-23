import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestVisitBookingComponent } from './guest-visit-booking.component';

describe('GuestVisitBookingComponent', () => {
  let component: GuestVisitBookingComponent;
  let fixture: ComponentFixture<GuestVisitBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GuestVisitBookingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GuestVisitBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
