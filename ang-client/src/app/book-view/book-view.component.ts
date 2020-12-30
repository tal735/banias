import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-view',
  templateUrl: './book-view.component.html',
  styleUrls: ['./book-view.component.css']
})
export class BookViewComponent implements OnInit {
  form: FormGroup = this.formBuilder.group({
    dateFrom: new FormControl('', Validators.required),
    dateTo: new FormControl('', Validators.required),
    guests: new FormControl('', Validators.required)
  });
  booking : any;
  notes: any = [];
  message: string;

  constructor(private route: ActivatedRoute, private networkService : NetworkService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    const bookingId = this.route.snapshot.paramMap.get('id');
    this.networkService.getBooking(bookingId).subscribe(
      data => {
        this.handleBookingResponse(data);
        this.fetchNotes();
      },
      error => console.log('error: ' + error)
    );
  }

  fetchNotes() {
    let notesOffset = 0;//this.notes.length;
    this.networkService.getNotes(this.booking.id, notesOffset).subscribe(
      data => {this.notes = data;},
      error => console.log('error: ' + error)
    );
  }

  onFormSubmit() {
    if (this.form.invalid) {
      alert('Please make sure all fields are filled.');
      return;
    }
    if (confirm("Any changes will require your booking to be reviewed and approved. Are you sure?")) {
      let bookingRequest = {
        dateFrom: new Date(this.form.value.dateFrom).getTime(),
        dateTo:new Date(this.form.value.dateTo).getTime(),
        guests: this.form.value.guests
      }
      console.log(bookingRequest);
      this.networkService.updateBooking(this.booking.id, bookingRequest).subscribe(
        data => {this.handleBookingResponse(data);},
        error => {console.log('error:' + error)}
      );
    }
    
  }

  submitMessage() {
    this.networkService.postNote(this.booking.id, this.message).subscribe(
      data => {this.message = null; this.fetchNotes();},
      error => console.log('error:'+error)
    );
  }

  handleBookingResponse(data) {
    this.booking = data;
    this.form.patchValue({
        dateFrom: new Date(this.booking.dateFrom),
        dateTo: new Date(this.booking.dateTo),
        guests: this.booking.guests
      }
    );
  }
}
