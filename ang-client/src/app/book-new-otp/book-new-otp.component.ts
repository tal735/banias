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

  email : string = null;
  emailSent : boolean = false;
  error : string = null;
  message : string = null;

  constructor(public authenticationService : AuthenticationService, private networkService : NetworkService, private router : Router, private _location: Location) { }

  ngOnInit(): void {
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
    this.emailSent = false;
    this.networkService.requestBookingOtp(this.email).subscribe(
      data => {
        this.emailSent = true;
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
          }
        );
      },
      error => {
        this.error = "Code is invalid.";
      }
    );
  }

}
