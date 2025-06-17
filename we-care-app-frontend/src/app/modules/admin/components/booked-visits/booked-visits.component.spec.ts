import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookedVisitsComponent } from './booked-visits.component';

describe('BookedVisitsComponent', () => {
  let component: BookedVisitsComponent;
  let fixture: ComponentFixture<BookedVisitsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookedVisitsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookedVisitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
