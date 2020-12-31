import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  token: string;
  userId : number = null;
  error: string;
  message: string;

  constructor(private route: ActivatedRoute, private networkService : NetworkService) { }

  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token');
    let i = this.token.indexOf('|');
    if (i > 0) {
      let userIdStr = this.token.substring(0, i);
      this.userId = +userIdStr;
      this.token = this.token.substring(i + 1);
    }
  }

  resetPassword(password, confirm) {
    this.error = null;
    this.message = null;
    
    let pass = new String(password.value).valueOf();
    if (pass !== new String(confirm.value).valueOf()) {
      this.error = "Passwords do not match, try agagin.";
      return;
    }
    if (pass.length < 6) {
      this.error = "Password is too weak.";
      return;
    }

    this.networkService.resetPassword(this.token, this.userId, pass).subscribe(
      data => {this.message = "Password Changed Succesfully."},
      error => {
        this.error = error.error;/*"There was an error. Try Again."*/
        console.log(error);}
    );
  }

}
