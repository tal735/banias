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
    return this.http.post('/authentication',body.toString(), { withCredentials: true, headers : headers, responseType : 'text', observe: 'response'});
  }

  public logout() {
    return this.http.post('/logout', {withCredentials: true, observe : 'response'});
  }

  public otpLogin(username : string, password : string) {
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    return this.http.post('/api/authentication',body.toString(), { withCredentials: true, headers : headers, responseType : 'text', observe: 'response'});
  }

  public otpLogout() {
    return this.http.post('/api/logout', {withCredentials: true, observe : 'response'});
  }

  public getBooking(reference) {
    return this.http.get(`/api/booking/${reference}`, { responseType: 'json'});
  }

  public updateBooking(reference, bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post(`/api/booking/${reference}`, bookingRequest, {'headers':headers , responseType: 'json'});
  }

  public book(bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post('/api/booking', bookingRequest, {'headers': headers , responseType: 'json'});
  }

  public getNotes(reference, offset) {
    let params = new HttpParams();
    if (offset != null) {
      params = params.append('offset', offset);
    }
    return this.http.get(`/api/booking/notes/${reference}`, { params : params, responseType: 'json'});
  }

  public postNote(reference, note) {
    return this.http.post(`/api/booking/notes/${reference}`, note);
  }

  public adminBookingSearch(searchRequest) {
    return this.http.post('/admin/booking/find', searchRequest);
  }

  public adminGetBooking(id) {
    return this.http.get(`/admin/booking/${id}`, { responseType: 'json'});
  }

  public adminAddBooking(bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post('/admin/booking', bookingRequest, {'headers': headers , responseType: 'json'});
  }

  public adminUpdateBooking(id, bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post(`/admin/booking/${id}`, bookingRequest, {'headers':headers , responseType: 'json'});
  }

  public adminGetNotes(id, offset) {
    let params = new HttpParams();
    if (offset != null) {
      params = params.append('offset', offset);
    }
    return this.http.get(`/admin/booking/${id}/notes`, { params : params, responseType: 'json'});
  }

  public adminPostNote(id, note) {
    return this.http.post(`/admin/booking/${id}/notes`, note);
  }

  public requestBookingOtp(email) {
    return this.http.post('/otp/book' ,email);
  }

  public requestViewOtp(reference) {
    return this.http.post('/otp/view' ,reference);
  }

  public adminUserExists(email) {
    let params = new HttpParams().append('email', email);
    return this.http.get(`/admin/user/exists`, { params : params, responseType: 'json'});
  }

  public adminUserCreate(email) {
    let params = new HttpParams().append('email', email);
    return this.http.post(`/admin/user/create`, { params : params, responseType: 'json'});
  }
}
