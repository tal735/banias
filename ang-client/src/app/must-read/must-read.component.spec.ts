import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MustReadComponent } from './must-read.component';

describe('MustReadComponent', () => {
  let component: MustReadComponent;
  let fixture: ComponentFixture<MustReadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MustReadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MustReadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
