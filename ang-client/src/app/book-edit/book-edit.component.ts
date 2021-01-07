import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  form: FormGroup = this.formBuilder.group({
    dateFrom: new FormControl('', Validators.required),
    dateTo: new FormControl('', Validators.required),
    guests: new FormControl('', Validators.required),
    contactName: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required)
  });
  booking : any = null;
  notes: any = [];
  error: string;
  message: string;

  constructor(private router : Router, private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.networkService.getBooking2().subscribe(
      data => {
        this.handleBookingResponse(data);
        this.fetchNotes();
      },
      error => console.log('error: ' + error)
    );
  }

  handleBookingResponse(data) {
    this.booking = data;
    this.form.patchValue({
        dateFrom: new Date(data.dateFrom).toISOString(),
        dateTo: new Date(data.dateTo).toISOString(),
        guests: data.guests,
        contactName: data.contactName,
        phone: data.phone
      }
    );
  }

  onFormSubmit() {
    this.error = null;
    let bookingRequest = {
      dateFrom: new Date(this.form.value.dateFrom).getTime(),
      dateTo:new Date(this.form.value.dateTo).getTime(),
      guests: this.form.value.guests,
      contactName: this.form.value.contactName,
      phone: this.form.value.phone,
    }
    this.networkService.updateBooking2(bookingRequest).subscribe(
      data => {
        this.handleBookingResponse(data);
      },
      error => {this.error = error.error.error; console.log(error.error.error)}
    );
  }

  submitMessage() {
    this.networkService.postNote2(this.message).subscribe(
      data => {
        this.message = null; 
        this.fetchNotes();
      },
      error => console.log('error:'+error)
    );
  }

  fetchNotes() {
    let maxId = null;
    if (this.notes.length > 0) {
      maxId = Math.max.apply(Math, this.notes.map(function(o) { return o.id; }));
    }
    this.networkService.getNotes2(maxId).subscribe(
      data => {this.notes = this.notes.concat(data);},
      error => console.log('error: ' + error)
    );
  }

}
