import { AuthenticationService } from './authentication.service';
import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class Interceptor implements HttpInterceptor {

constructor(private authenticationService :  AuthenticationService) {}

intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    //if (this.authenticationService.authenticated) {
    if (request.url.startsWith('/')) {
        const copiedReq = request.clone({
            withCredentials: true
        });
        return next.handle(copiedReq);
    }

    return next.handle(request);
    }
}
