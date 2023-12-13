import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    const authToken: string | null = localStorage.getItem('AuthorizationToken');

    if (authToken) {
      const authRequest: HttpRequest<any> = request.clone({
        setHeaders: {
          Authorization: `${authToken}`
        }
      });
      return next.handle(authRequest);
    }

    return next.handle(request);
  }
}
