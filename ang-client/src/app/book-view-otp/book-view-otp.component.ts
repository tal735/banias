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
  otpVerifyError : string = null;
  otpRequestError : string = null;
  emailSent : boolean = false;

  constructor(private authenticationService : AuthenticationService, private networkService : NetworkService, private router : Router, private _location: Location) { }

  ngOnInit(): void {
  }


  requestOtp(element) {
    this.reference = element.value;
    if (!this.reference || this.reference.length == 0) {
      this.otpVerifyError = "Please provide a reference number.";
      return;
    }
    if (this.authenticationService.isAuthenticated()) {
      this.step = 2;
    } else {
      let resend = false;
      this.sendOtpEmail(resend);
    }
  }

  sendOtpEmail(resend) {
    this.otpRequestError = null;
    this.otpVerifyError = null;
    this.emailSent = false;
    this.networkService.requestViewOtp(this.reference).subscribe(
      data => {
        if (!resend) {
          this.step++;
        }
        this.emailSent = true;
      },
      error => {this.otpRequestError = error.error}
    );
  }

  verifyOtp(otp) {
    if (!otp.value || otp.value.length == 0) {
      this.otpVerifyError = "Please provide code.";
      return;
    }
    this.networkService.otpLogin(this.reference, otp.value).subscribe(
      data => {
        this.networkService.getUser().subscribe(
          response => {
            this.authenticationService.setUser(response);
            this.step++;
          }
        );
      },
      error => {
        this.otpVerifyError = "Code is invalid.";
      }
    );
  }

}
