import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

   bookings : any = [];

   constructor(private networkService : NetworkService, public authService : AuthenticationService) { }
 
   ngOnInit(): void {
	this.loadBookings();
   }

   loadBookings() {
	   let offset = this.bookings.length;
	   this.networkService.getBookings(offset).subscribe(
		   data => this.bookings = this.bookings.concat(data),
		   error => console.log('error: ' + error)
	   );
   }

   bookingUpdatedEvent(booking) {
      let index = this.bookings.findIndex(x => x.id == booking.id);
      this.bookings[index] = booking;
   }
}
