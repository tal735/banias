import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from './service/auth-guard.service'
import { BookViewComponent } from './book-view/book-view.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';
import { BookNewOtpComponent } from './book-new-otp/book-new-otp.component';
import { BookViewOtpComponent } from './book-view-otp/book-view-otp.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component : HomeComponent},
  {path: 'login', component : LoginComponent},
  {path: 'booking/:id', component : BookViewComponent, canActivate: [AuthGuardService]},
  {path: 'book-new-otp', component : BookNewOtpComponent},
  {path: 'book-view-otp', component : BookViewOtpComponent},
  {path: 'admin', component : AdminHomeComponent, canActivate: [AuthGuardService]},
  {path: 'admin/admin-booking', component : AdminBookingComponent, canActivate: [AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
