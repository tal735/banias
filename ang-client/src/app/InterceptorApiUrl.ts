import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../environments/environment" // Change this to your file location

@Injectable()
export class InterceptorApiUrl implements HttpInterceptor {

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (request.url.startsWith('/')) {
            request = request.clone({
              url: environment.apiUrl + request.url
            });
        }
        return next.handle(request);
    }

}
