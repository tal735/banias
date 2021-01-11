import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminBookingAddComponent } from './admin-booking-add.component';

describe('AdminBookingAddComponent', () => {
  let component: AdminBookingAddComponent;
  let fixture: ComponentFixture<AdminBookingAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminBookingAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminBookingAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
