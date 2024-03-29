import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-admin-booking',
  templateUrl: './admin-booking.component.html',
  styleUrls: ['./admin-booking.component.css']
})
export class AdminBookingComponent implements OnInit {

  searchForm: FormGroup = this.formBuilder.group({
    dateFromMin: new FormControl(NaN),
    dateFromMax: new FormControl(NaN),
    dateToMin: new FormControl(NaN),
    dateToMax: new FormControl(NaN),
    email: new FormControl('', Validators.nullValidator),
    reference: new FormControl('', Validators.nullValidator)
  });

  searchRequest : any = null;

  bookings : any = [];

  constructor(private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  onFormSubmit() {
    this.bookings = [];
    this.searchRequest = {
      dateFromMin: new Date(this.searchForm.value.dateFromMin).getTime(),
      dateFromMax:new Date(this.searchForm.value.dateFromMax).getTime(),
      dateToMin:new Date(this.searchForm.value.dateToMin).getTime(),
      dateToMax:new Date(this.searchForm.value.dateToMax).getTime(),
      email:this.searchForm.value.email,
      reference:this.searchForm.value.reference
    };
    this.loadMoreBookings();
  }

  getStatusClass(status) {
    if (status.toUpperCase() === 'APPROVED') {
      return 'alert-success';
    }
    if (status.toUpperCase() === 'PENDING') {
      return 'alert-warning';
    }
    if (status.toUpperCase() === 'CANCELLED') {
      return 'alert-danger';
    }
  }

  bookingUpdatedEvent(booking) {
    let index = this.bookings.findIndex(x => x.id == booking.id);
    this.bookings[index] = booking;
 }

 loadMoreBookings() {
  this.searchRequest.offset = this.bookings.length;
  this.networkService.adminBookingSearch(this.searchRequest).subscribe(
    data => this.bookings = this.bookings.concat(data),
    error => console.log(error)
  );
 }
}
