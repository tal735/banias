import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuardService } from './service/auth-guard.service'
import { BookViewComponent } from './book-view/book-view.component';
import { BookNewComponent } from './book-new/book-new.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component : HomeComponent},
  {path: 'login', component : LoginComponent},
  {path: 'booking/:id', component : BookViewComponent, canActivate: [AuthGuardService]},
  {path: 'book-new', component : BookNewComponent, canActivate: [AuthGuardService]},
  {path: 'admin', component : AdminHomeComponent, canActivate: [AuthGuardService]},
  {path: 'admin/admin-booking', component : AdminBookingComponent, canActivate: [AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
