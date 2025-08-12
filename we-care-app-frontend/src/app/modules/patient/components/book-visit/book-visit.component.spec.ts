import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookVisitComponent } from './book-visit.component';

describe('BookVisitComponent', () => {
  let component: BookVisitComponent;
  let fixture: ComponentFixture<BookVisitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookVisitComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BookVisitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
