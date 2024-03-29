import { Component } from '@angular/core';
import { AuthenticationService } from './authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'banias-camping';

  constructor(private authService : AuthenticationService) {}

  ngOnInit(): void {
    this.authService.checkAuthentication();
  }
}
