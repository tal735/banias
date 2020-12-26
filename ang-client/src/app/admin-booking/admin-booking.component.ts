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
    dateFrom: new FormControl(NaN, Validators.nullValidator),
    dateTo: new FormControl(NaN, Validators.nullValidator)
  });

  bookings : any = [];

  constructor(private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  onFormSubmit() {
    let searchRequest = {
      dateFrom: new Date(this.searchForm.value.dateFrom).getTime(),
      dateTo:new Date(this.searchForm.value.dateTo).getTime()
    };
    console.log(searchRequest);
    this.networkService.adminBookingSearch(searchRequest.dateFrom, searchRequest.dateTo).subscribe(
      data => this.bookings = data,
      error => console.log(error)
      
    );
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
}
