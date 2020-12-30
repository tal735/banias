import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminBookingEditModalComponent } from './admin-booking-edit-modal.component';

describe('AdminBookingEditModalComponent', () => {
  let component: AdminBookingEditModalComponent;
  let fixture: ComponentFixture<AdminBookingEditModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminBookingEditModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminBookingEditModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
