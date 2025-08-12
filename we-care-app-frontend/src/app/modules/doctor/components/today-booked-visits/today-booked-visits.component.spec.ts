import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodayBookedVisitsComponent } from './today-booked-visits.component';

describe('TodayBookedVisitsComponent', () => {
  let component: TodayBookedVisitsComponent;
  let fixture: ComponentFixture<TodayBookedVisitsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TodayBookedVisitsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TodayBookedVisitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
