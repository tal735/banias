import { AuthenticationService } from './../authentication.service';
import { Component, OnInit } from '@angular/core';
import { NetworkService } from '../network.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : string = null;
  password : string = null;

  constructor(private authService : AuthenticationService, private networkService : NetworkService, private router : Router) { }

  ngOnInit(): void {
  }

  login() {
    this.networkService.login(this.username, this.password).subscribe(
      data => {
        this.networkService.getUser().subscribe(
          response => {
            this.authService.setUser(response);
            let redirect = this.authService.redirect;
            if (!redirect) {
              redirect = '/home';
            }
            this.router.navigate([redirect]);
          }
        );
      },
      error => {
        console.log(error);
      }
    );
  }

}
