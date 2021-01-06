import { InterceptorApiUrl } from './InterceptorApiUrl';
import { Interceptor } from './interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AuthenticationService } from './authentication.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BookViewComponent } from './book-view/book-view.component';
import { BookNewComponent } from './book-new/book-new.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminBookingComponent } from './admin-booking/admin-booking.component';
import { AdminBookingEditModalComponent } from './admin-booking-edit-modal/admin-booking-edit-modal.component';
import { AdminBookingMessageModalComponent } from './admin-booking-message-modal/admin-booking-message-modal.component';
import { BookNewOtpComponent } from './book-new-otp/book-new-otp.component';
import { BookViewOtpComponent } from './book-view-otp/book-view-otp.component';
import { BookEditComponent } from './book-edit/book-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    BookViewComponent,
    BookNewComponent,
    AdminHomeComponent,
    AdminBookingComponent,
    AdminBookingEditModalComponent,
    AdminBookingMessageModalComponent,
    BookNewOtpComponent,
    BookViewOtpComponent,
    BookEditComponent
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
