import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviousVisitsComponent } from './previous-visits.component';

describe('PreviousVisitsComponent', () => {
  let component: PreviousVisitsComponent;
  let fixture: ComponentFixture<PreviousVisitsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreviousVisitsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PreviousVisitsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
