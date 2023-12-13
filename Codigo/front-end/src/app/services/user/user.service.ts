import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, Observable, tap } from 'rxjs';

import { API_CONFIG } from '../config';
import { AuthService } from '../auth/auth.service';
import { User } from '../../interfaces/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  login(user: User): Observable<any> {
    return this.http.post<User>(`${API_CONFIG.baseUrl}/login`, user, { observe: 'response' })
      .pipe(
        tap(response => {
          const authToken: string | null = response.headers.get('Authorization');
          this.authService.login(<string>authToken);
          console.log('Usuario logado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na login do usuário', err);
          throw err;
        })
      );
  }

  logout(): void {
    const authService: AuthService = new AuthService();
    authService.logout();
    console.log('Usuario deslogado com sucesso!');
  }

  createUser(user: User): Observable<any> {
    return this.http.post<User>(`${API_CONFIG.baseUrl}/user`, user)
      .pipe(
        tap(response => {
          console.log('Usuario criado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação do usuário', err);
          throw err;
        })
      );
  }

  findCurrentUser(): Observable<User> {
    return this.http.get<User>(`${API_CONFIG.baseUrl}/user/me`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção do usuário', err);
          throw err;
        })
      )
  }
}
