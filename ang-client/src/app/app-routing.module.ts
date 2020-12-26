import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookComponent } from './book/book.component';
import { AuthGuardService } from './service/auth-guard.service'
import { BookViewComponent } from './book-view/book-view.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ProfileComponent } from './profile/profile.component';
import { BookNewComponent } from './book-new/book-new.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  // {path: '/admin/**', canActivate: [AuthGuardService]},
  {path: 'home', component : HomeComponent},
  {path: 'login', component : LoginComponent},
  {path: 'signup', component : SignupComponent},
  {path: 'forgot-password', component : ForgotPasswordComponent},
  {path: 'reset-password/:token', component : ResetPasswordComponent},
  {path: 'booking', component : BookComponent, canActivate: [AuthGuardService]},
  {path: 'booking/:id', component : BookViewComponent, canActivate: [AuthGuardService]},
  {path: 'profile', component : ProfileComponent, canActivate: [AuthGuardService]},
  {path: 'book-new', component : BookNewComponent, canActivate: [AuthGuardService]},
  {path: 'admin', component : AdminHomeComponent, canActivate: [AuthGuardService]},
  {path: 'admin/admin-user', component : AdminUserComponent, canActivate: [AuthGuardService]},
  {path: 'admin/admin-booking', component : AdminBookingComponent, canActivate: [AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
