import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from './service/auth-guard.service'
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';
import { BookNewOtpComponent } from './book-new-otp/book-new-otp.component';
import { BookViewOtpComponent } from './book-view-otp/book-view-otp.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { MustReadComponent } from './must-read/must-read.component';
import { AdminBookingAddComponent } from './admin-booking-add/admin-booking-add.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component : HomeComponent},
  {path: 'login', component : LoginComponent},
  {path: 'contact-us', component : ContactUsComponent},
  {path: 'must-read', component : MustReadComponent},
  {path: 'book-new-otp', component : BookNewOtpComponent},
  {path: 'book-view-otp', component : BookViewOtpComponent},
  {path: 'admin', component : AdminHomeComponent, canActivate: [AuthGuardService]},
  {path: 'admin/manage-booking', component : AdminBookingComponent, canActivate: [AuthGuardService]},
  {path: 'admin/add-booking', component : AdminBookingAddComponent, canActivate: [AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
