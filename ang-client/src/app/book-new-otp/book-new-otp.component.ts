import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-book-new-otp',
  templateUrl: './book-new-otp.component.html',
  styleUrls: ['./book-new-otp.component.css']
})
export class BookNewOtpComponent implements OnInit {

  step : number = 0;
  email : string = null;
  error : string = null;

  constructor(private authenticationService : AuthenticationService, private networkService : NetworkService, private router : Router, private _location: Location) { }

  ngOnInit(): void {
    if (this.authenticationService.isAuthenticated()) {
      this.step = 2;
    }
  }

  requestOtp(element) {
    this.error = null;
    this.email = element.value;
    if (!this.email || this.email.length == 0) {
      this.error = "Please provide an Email.";
      return;
    }
    this.sendOtpEmail(false);
  }

  sendOtpEmail(resend) {
    this.networkService.requestBookingOtp(this.email).subscribe(
      data => {
        if (!resend) {
          this.step++
        }
      },
      error => {this.error = error.error}
    );
  }

  verifyOtp(otp) {
    if (!otp.value || otp.value.length == 0) {
      this.error = "Please provide code.";
      return;
    }
    this.networkService.otpLogin(this.email, otp.value).subscribe(
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
