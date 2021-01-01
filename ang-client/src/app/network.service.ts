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

  public getBooking(id) {
    return this.http.get('/api/booking/' + id, { responseType: 'json'});
  }

  public updateBooking(bookingId, bookingRequest) {
    const headers = { 'content-type': 'application/json'}  
    return this.http.post('/api/booking/' + bookingId, bookingRequest, {'headers':headers , responseType: 'json'});
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

  public adminBookingSearch(searchRequest) {
    return this.http.post('/admin/booking/find', searchRequest);
  }

}
