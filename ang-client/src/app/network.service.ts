import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  constructor(private http : HttpClient) { }


  public getUser() {
    return this.http.get('/user', {withCredentials: true, responseType : 'json'});
  }

  public login(username : string, password : string) {
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post('/api/authentication',body.toString(), { withCredentials: true, headers : headers, responseType : 'text', observe: 'response'});
  }

  public logout() {
    return this.http.post('/api/logout', {withCredentials: true, observe : 'response'});
  }

  public getHome() {
    return this.http.get('/home', {responseType: 'text'});
  }

  public getBookings(offset) {
    const params = new HttpParams().append('offset', offset);
    return this.http.get('/api/booking', { params : params , responseType: 'json'});
  }

  public getBooking(id) {
    return this.http.get('/api/booking/' + id, { responseType: 'json'});
  }

  public updateBooking(bookingId, bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(bookingRequest);
    console.log('body=' + body);
    return this.http.post('/api/booking/' + bookingId, body, {'headers':headers , responseType: 'json'});
  }

  public book(bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post('/api/booking/', bookingRequest, {'headers': headers , responseType: 'json'});
  }

  public getNotes(bookingId, offset) {
    const params = new HttpParams().append('offset', offset);
    return this.http.get('/api/booking/notes/' + bookingId, { params : params, responseType: 'json'});
  }

  public postNote(bookingId, note) {
    return this.http.post('/api/booking/notes/' + bookingId, note);
  }

  public forgotPassword(email) {
    return this.http.post('/forgot-password', email);
  }

  public resetPassword(token, password) {
    const body = {
      token : token,
      password: password
    }
    const headers = { 'content-type': 'application/json'}  
    return this.http.post('/reset-password', JSON.stringify(body), {'headers':headers});
  }

  public signup(email, password) {
    const params = new HttpParams().append('email', email).append('password', password);
    return this.http.post('/user/register', null, { params : params , responseType: 'json'});
  }

  public updateUserNames(firstName, lastName) {
    const params = new HttpParams().append('firstName', firstName).append('lastName', lastName);
    return this.http.post('/api/user/update-name', null, { params : params, responseType: 'json'});
  }

  public updateUserEmail(email) {
    const params = new HttpParams().append('email', email);
    return this.http.post('/api/user/update-email', null, { params : params, responseType: 'json'});
  }

  public updateUserPhone(countryCode, phone) {
    const params = new HttpParams().append('countryCode', countryCode).append('phone', phone);
    return this.http.post('/api/user/update-phone', null, { params : params, responseType: 'json'});
  }

  public sendPhoneToken() {
    return this.http.post('/api/user/token', null, { responseType: 'text'});
  }

  public verifyPhoneToken(token) {
    const params = new HttpParams().append('token', token);
    return this.http.post('/api/user/verify', null, { params : params });
  }

  public adminUserSearch(email) {
    const params = new HttpParams().append('email', email);
    return this.http.post('/admin/user/find', null, { params : params });
  }

  public adminUserUpdate(email,value,field) {
    const params = new HttpParams().append('email', email).append('value', value).append('field', field);
    return this.http.post('/admin/user/update', null, { params : params });
  }

  public adminBookingSearch(searchRequest) {
    return this.http.post('/admin/booking/find', searchRequest);
  }

  public adminUpdateBooking(bookingId, bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(bookingRequest);
    console.log('body=' + body);
    return this.http.post('/admin/booking/' + bookingId, body, {'headers':headers , responseType: 'json'});
  }
}
