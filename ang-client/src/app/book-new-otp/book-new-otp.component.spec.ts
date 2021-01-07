import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookNewOtpComponent } from './book-new-otp.component';

describe('BookNewOtpComponent', () => {
  let component: BookNewOtpComponent;
  let fixture: ComponentFixture<BookNewOtpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookNewOtpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookNewOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
