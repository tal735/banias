import { Component, OnInit } from '@angular/core';
import { NetworkService } from '../network.service';

@Component({
  selector: 'app-admin-user',
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css']
})
export class AdminUserComponent implements OnInit {

  email : string = '';
  user : any = null;
  userEmail : string = null;
  userTypes = ['REGULAR', 'BLACKLISTED'];

  constructor(private networkService : NetworkService) { }

  ngOnInit(): void {
  }

  searchSubmit() {
    this.user = null;
    this.networkService.adminUserSearch(this.email).subscribe(
      data =>  this.handleResponse(data),
      error => console.log(error)
    );
  }

  updateField(value, field) {
    console.log(field + ':' + value);
    this.networkService.adminUserUpdate(this.userEmail, value, field).subscribe(
      data => this.handleResponse(data),
      error => console.log(error)
    );
  }
  handleResponse(data) {
    this.user = data;
    this.userEmail = this.user.email;
    console.log(this.user);
  }


}
