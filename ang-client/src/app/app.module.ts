import { InterceptorApiUrl } from './InterceptorApiUrl';
import { Interceptor } from './interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AuthenticationService } from './authentication.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';
import { AdminBookingEditModalComponent } from './admin-booking-edit-modal/admin-booking-edit-modal.component';
import { AdminBookingMessageModalComponent } from './admin-booking-message-modal/admin-booking-message-modal.component';
import { BookNewOtpComponent } from './book-new-otp/book-new-otp.component';
import { BookViewOtpComponent } from './book-view-otp/book-view-otp.component';
import { BookEditComponent } from './book-edit/book-edit.component';
import { BookNewComponent } from './book-new/book-new.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { AdminBookingAddComponent } from './admin-booking-add/admin-booking-add.component';
import { GuidelinesComponent } from './guidelines/guidelines.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    AdminHomeComponent,
    AdminBookingComponent,
    AdminBookingEditModalComponent,
    AdminBookingMessageModalComponent,
    BookNewOtpComponent,
    BookViewOtpComponent,
    BookEditComponent,
    BookNewComponent,
    ContactUsComponent,
    AdminBookingAddComponent,
    GuidelinesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [
    AuthenticationService,
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorApiUrl, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
