import { Component, OnInit } from '@angular/core';
import { DateRange } from '@angular/material/datepicker';
import { AuthenticationService } from '../authentication.service';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  codeSent : boolean = false;
  message : string = null;

  constructor(private networkService : NetworkService, public authService : AuthenticationService) { }

  ngOnInit(): void {
  }


  updateName(firstName, lastName) {
    this.networkService.updateUserNames(firstName.value, lastName.value).subscribe(
      data => {this.authService.setUser(data);},
      error => {console.log('error: ' + error);}
      );
  }

  updateEmail(email) {
    this.networkService.updateUserEmail(email.value).subscribe(
      data => {this.authService.setUser(data);},
      error => {console.log('error: ' + error);}
    );
  }

  updatePhone(countryCode, phone) {
    if (!this.authService.user.verified || confirm('You will need to revalidate your phone number. Continue?')) {
      this.networkService.updateUserPhone(countryCode.value, phone.value).subscribe(
        data => {this.authService.setUser(data);},
        error => {console.log('error: ' + error);}
      );
    }
  }

  getCode() {
    this.message = null;
    this.networkService.sendPhoneToken().subscribe(
      data => {this.codeSent = true;},
      error => {this.codeSent = false;}
    );
  }

  verify(token) {
    this.message = null;
    this.networkService.verifyPhoneToken(token.value).subscribe(
      data => {
        this.authService.setUser(data); 
        this.codeSent = false;
        token.value = null;
        document.getElementById('modalDismissButton').click();
      },
      error => {this.message = error.error;}
    );
  }
}
