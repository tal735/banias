import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';;
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-admin-booking-add',
  templateUrl: './admin-booking-add.component.html',
  styleUrls: ['./admin-booking-add.component.css']
})
export class AdminBookingAddComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private networkService : NetworkService, private _location: Location) { }

  form: FormGroup;
  statuses = ['APPROVED', 'PENDING', 'CANCELLED'];
  error : string = null;
  
  responseBooking : any = null;
  manualEmail : string = '';

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      dateFrom: new FormControl('', Validators.required),
      dateTo: new FormControl('', Validators.required),
      guests: new FormControl('', Validators.required),
      status: new FormControl('', Validators.required),
      contactName: new FormControl('', Validators.required),
      phone: new FormControl('', Validators.required),
      email: new FormControl('', Validators.nullValidator)
    });
  }

  onFormSubmit() {
    const input = document.getElementById('manualEmailRadio') as HTMLInputElement;
    if (input.checked) {
      this.setEmailValue(this.manualEmail);
    }

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
      email: this.form.value.email
    }

    this.networkService.adminAddBooking(bookingRequest).subscribe(
      data => {
        this.responseBooking = data;
      },
      error => {
        this.error = error.error.error; 
      }
      );
    }

    setEmailValue(email) {
      this.form.patchValue({
        email : email
      });
    }

    backClicked() {
      this._location.back();
    }

}
