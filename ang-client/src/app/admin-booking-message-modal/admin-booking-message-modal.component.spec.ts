import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminBookingMessageModalComponent } from './admin-booking-message-modal.component';

describe('AdminBookingMessageModalComponent', () => {
  let component: AdminBookingMessageModalComponent;
  let fixture: ComponentFixture<AdminBookingMessageModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminBookingMessageModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminBookingMessageModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
