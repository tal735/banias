import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.css']
})
export class BookNewComponent implements OnInit {

  form: FormGroup = this.formBuilder.group({
    dateFrom: new FormControl('', Validators.required),
    dateTo: new FormControl('', Validators.required),
    guests: new FormControl('', Validators.required),
    note: new FormControl('', Validators.nullValidator),
    contactName: new FormControl('', Validators.required),
    phone: new FormControl('', Validators.required)
  });

  reference : string = null;
  error : string = null;

  constructor(private router : Router, private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  onFormSubmit() {
    this.error = null;
    let bookingRequest = {
      dateFrom: new Date(this.form.value.dateFrom).getTime(),
      dateTo:new Date(this.form.value.dateTo).getTime(),
      guests: this.form.value.guests,
      contactName: this.form.value.contactName,
      phone: this.form.value.phone,
      note : this.form.value.note
    }
    this.networkService.book(bookingRequest).subscribe(
      data => {
        this.handleResponse(data);
      },
      error => {this.error = error.error.error; console.log(error.error.error)}
    );
  }

  handleResponse(data) {
    this.reference = data.reference;
  }

  goHome() {
    this.router.navigate(["/home"]);
  }
}
