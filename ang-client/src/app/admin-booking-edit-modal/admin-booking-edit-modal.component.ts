import { DatePipe } from '@angular/common';
import { Component, OnInit, Input, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-admin-booking-edit-modal',
  templateUrl: './admin-booking-edit-modal.component.html',
  styleUrls: ['./admin-booking-edit-modal.component.css']

})
export class AdminBookingEditModalComponent implements OnInit {
  @Input() id : any;
  @Output() bookingUpdatedEvent = new EventEmitter<any>();
  @ViewChild('myModal') myModal: ElementRef;

  form: FormGroup;

  booking : any = null;
  statuses = ['APPROVED', 'PENDING', 'CANCELLED'];
  dp = new DatePipe(navigator.language);

  error : string = null;

  modalRef : NgbModalRef = null;

  constructor(private modalService: NgbModal, private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  openModal() {
    this.modalRef = this.modalService.open(this.myModal, { backdrop: 'static'});
    this.form = this.formBuilder.group({
      dateFrom: new FormControl('', Validators.required),
      dateTo: new FormControl('', Validators.required),
      guests: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
      contactName: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required)
    });
    this.getBooking();
}

getBooking() {
  this.networkService.adminGetBooking(this.id).subscribe(
    data => this.handleBookingResponse(data),
    error => {
      this.error = error.error.error;
      console.log(error.error.error)
    }
  );
}

onFormSubmit() {
  if (this.form.invalid) {
    alert('Please make sure all fields are filled.');
    return;
  }
  let bookingRequest = {
    dateFrom: new Date(this.form.value.dateFrom).getTime(),
    dateTo:new Date(this.form.value.dateTo).getTime(),
    guests: this.form.value.guests,
    status: this.form.value.status,
    contactName: this.form.value.contactName,
    phone: this.form.value.phone,
    userId: this.booking.userId
  }
  this.networkService.adminUpdateBooking(this.booking.id, bookingRequest).subscribe(
    data => {
      this.handleBookingResponse(data);
      this.bookingUpdatedEvent.emit(this.booking);
      this.modalRef.close();
    },
    error => {
      this.error = error.error.error; 
      console.log(error.error.error)
    }
    );
  }

  handleBookingResponse(data) {
    this.booking = data;
    this.form.patchValue(
      {
        dateFrom: this.dp.transform(new Date(this.booking.dateFrom), 'y-MM-dd'),
        dateTo: this.dp.transform(new Date(this.booking.dateTo), 'y-MM-dd'),
        guests: this.booking.guests,
        status: this.booking.status,
        contactName: this.booking.contactName,
        phone: this.booking.phone
      }
    );
  }

}
