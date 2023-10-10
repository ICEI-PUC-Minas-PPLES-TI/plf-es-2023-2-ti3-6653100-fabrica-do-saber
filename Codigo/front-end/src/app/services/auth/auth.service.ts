import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private AUTH_TOKEN_KEY: string = 'AuthorizationToken';
  private AUTH_TOKEN: string = '';

  login(AUTH_TOKEN: string): void {
    this.AUTH_TOKEN = AUTH_TOKEN;
    localStorage.setItem(this.AUTH_TOKEN_KEY, this.AUTH_TOKEN);
  }

  logout(): void {
    localStorage.removeItem(this.AUTH_TOKEN_KEY);
  }

  isAuthenticated():boolean {
    return !!localStorage.getItem('AuthorizationToken')
  }

}
