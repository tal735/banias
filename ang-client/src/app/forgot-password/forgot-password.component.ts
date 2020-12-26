import { Component, OnInit } from '@angular/core';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  error: string;
  message: string;

  constructor(private networkService : NetworkService) { }

  ngOnInit(): void {
  }

  sendEmail(element) {
    this.message = null;
    this.error = null;

    let email = element.value;
    console.log(email);
    
    if (!email || email.length === 0 || !email.trim()) {
      this.error = "Email is invalid";
    } else {
      this.networkService.forgotPassword(email).subscribe(
        data => {this.message = "Email Sent Succesfully."},
        error => {this.error = "There was an error. Try again."}
      )
    }
  }
}
