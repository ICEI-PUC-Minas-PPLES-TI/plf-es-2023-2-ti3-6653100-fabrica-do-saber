import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";
import {API_CONFIG} from "../config";
import {User} from "../../interfaces/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
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
