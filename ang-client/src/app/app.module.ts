import { InterceptorApiUrl } from './InterceptorApiUrl';
import { Interceptor } from './interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AuthenticationService } from './authentication.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { BookComponent } from './book/book.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BookViewComponent } from './book-view/book-view.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { ProfileComponent } from './profile/profile.component';
import { BookNewComponent } from './book-new/book-new.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    BookComponent,
    BookViewComponent,
    SignupComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    ProfileComponent,
    BookNewComponent,
    AdminHomeComponent,
    AdminUserComponent,
    AdminBookingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatInputModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthenticationService,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorApiUrl, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
