import { Component, OnInit } from '@angular/core';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private networkService : NetworkService) { }

  ngOnInit(): void {
  }

  signup(firstname, lastname, email, password, passwordConfirm) {
    if (password.value.length < 6 || !(password.value === password.value)) {
      alert('You should fill password accordingly');
      return;
    }
    this.networkService.signup(firstname.value, lastname.value, email.value, password.value).subscribe(
      data => {console.log("SUCCESS");},
      error => {console.log('ERROR')}
    );
  }
}
