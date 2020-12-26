import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.css']
})
export class BookNewComponent implements OnInit {

  form: FormGroup = this.formBuilder.group({
    dateFrom: new FormControl(null, Validators.required),
    dateTo: new FormControl(null, Validators.required),
    guests: new FormControl(null, Validators.required),
    note: new FormControl(null, Validators.nullValidator)
  });

  error : string = null;

  constructor(private networkService : NetworkService, private formBuilder: FormBuilder, private router : Router) { }

  ngOnInit(): void {
  }

  onFormSubmit() {
    this.error = null;
    let bookingRequest = {
      dateFrom: new Date(this.form.value.dateFrom).getTime(),
      dateTo:new Date(this.form.value.dateTo).getTime(),
      guests: this.form.value.guests,
      note: this.form.value.note
    }
    this.networkService.book(bookingRequest).subscribe(
      data => {this.router.navigate(["/booking"]);},
      error => {this.error = error.error.error; console.log(error.error.error)}
    );

  }
}
