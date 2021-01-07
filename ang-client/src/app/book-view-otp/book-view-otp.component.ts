import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-view-otp',
  templateUrl: './book-view-otp.component.html',
  styleUrls: ['./book-view-otp.component.css']
})
export class BookViewOtpComponent implements OnInit {

  step : number = 0;
  reference : string = null;
  error : string = null;

  constructor(private authenticationService : AuthenticationService, private networkService : NetworkService, private router : Router, private _location: Location) { }

  ngOnInit(): void {
  }


  requestOtp(element) {
    this.error = null;
    this.reference = element.value;
    if (!this.reference || this.reference.length == 0) {
      this.error = "Please provide a reference number.";
      return;
    }
    this.networkService.requestViewOtp(this.reference).subscribe(
      data => {this.step++},
      error => {this.error = error.error}
    );
  }

  verifyOtp(otp) {
    if (!otp.value || otp.value.length == 0) {
      this.error = "Please provide code.";
      return;
    }
    this.networkService.otpLogin(this.reference, otp.value).subscribe(
      data => {
        this.networkService.getUser().subscribe(
          response => {
            this.authenticationService.setUser(response);
            this.step++;
            console.log('GOOD!' + this.step);
          }
        );
      },
      error => {
        this.error = "Code is invalid.";
      }
    );
  }

  backClicked() {
    if (this.step == 0) {
      this._location.back();
    } else {
      this.step--;
    }
  }

}
