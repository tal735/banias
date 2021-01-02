import { Injectable } from '@angular/core';
import { NetworkService } from './network.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user : any;

  constructor(private networkService : NetworkService, private router : Router) { }

  setUser(user) {
    this.user = user;
  }

  login(username, password) {
    this.networkService.login(username, password).subscribe(
      () => {
        this.checkAuthentication();
      } 
    )
  }

  checkAuthentication() {
    this.networkService.getUser().subscribe(
      response => {
        this.setUser(response);
        if (this.isAuthenticated()) { /* move from here */
          this.router.navigate(["/home"]);
        }
      },
      error => {
        console.log('error: ' + error);
      }
    );
  }

  logout() {
    this.networkService.logout().subscribe(
      () => {
        this.setUser(null);
        this.router.navigate(["/home"]);
      }
    )
  }

  isAuthenticated() {
    return this.user && this.user.roles &&  this.user.roles.length > 0;
  }
  
  isAdmin() {
    return this.isAuthenticated() && this.user.roles.includes('ADMIN');
  }

  isVerified() {
    return this.isAuthenticated() && this.user.verified;
  }
}
