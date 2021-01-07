import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BookViewOtpComponent } from './book-view-otp.component';

describe('BookViewOtpComponent', () => {
  let component: BookViewOtpComponent;
  let fixture: ComponentFixture<BookViewOtpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BookViewOtpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BookViewOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
