import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, tap} from 'rxjs';
import {API_CONFIG} from '../configs/config';
import {User} from '../../interfaces/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  login(user: User): Observable<any> {
    return this.http.post<User>(`${API_CONFIG.baseUrl}/login`, user, {observe: 'response'})
      .pipe(
        tap(response => {
          const authToken: string | undefined = response.headers.get('Authorization')?.split(' ')[1];
          localStorage.setItem('AuthorizationToken', <string>authToken);
          console.log('Usuario logado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na login do usuário', err);
          throw err;
        })
      );
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
}
