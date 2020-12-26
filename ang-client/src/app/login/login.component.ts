import { AuthenticationService } from './../authentication.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : string = 'a@a.com';
  password : string = '123456';

  constructor(private authService : AuthenticationService) { }

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.username, this.password);
  }

}
