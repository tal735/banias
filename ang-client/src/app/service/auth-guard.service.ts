import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  routeURL: string;

  constructor(private authService: AuthenticationService, private router: Router) {
    this.routeURL = this.router.url;
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise((resolve, reject) => {
      if (!this.authService.isAuthenticated()) {
        this.routeURL = '/login';
        this.router.navigate(['/login']);
        return resolve(false);
      } else {
        this.routeURL = this.router.url;
        return resolve(true);
      }
    });
  }
}
