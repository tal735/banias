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
    this.networkService.signup(email.value, password.value).subscribe(
      data => {console.log("SUCCESS");},
      error => {console.log('ERROR')}
    );
  }
}
