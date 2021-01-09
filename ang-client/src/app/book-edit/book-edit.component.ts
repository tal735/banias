import { DatePipe } from '@angular/common'
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  @Input() reference : string;

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
  accessError : string = null;

  constructor(private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.networkService.getBooking(this.reference).subscribe(
      data => {
        this.handleBookingResponse(data);
        this.fetchNotes();
      },
      error => {
        console.log('error: ' + error);
        this.accessError = error.error;
    }
    );
  }

  handleBookingResponse(data) {
    this.booking = data;
    let dp = new DatePipe(navigator.language);
    this.form.patchValue({
        dateFrom: dp.transform(new Date(data.dateFrom), 'y-MM-dd'),
        dateTo: dp.transform(new Date(data.dateTo), 'y-MM-dd'),
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
    this.networkService.updateBooking(this.reference, bookingRequest).subscribe(
      data => {
        this.handleBookingResponse(data);
      },
      error => {this.error = error.error.error; console.log(error.error.error)}
    );
  }

  submitMessage() {
    this.networkService.postNote(this.reference, this.message).subscribe(
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
    this.networkService.getNotes(this.reference, maxId).subscribe(
      data => {this.notes = this.notes.concat(data);},
      error => console.log('error: ' + error)
    );
  }

}
