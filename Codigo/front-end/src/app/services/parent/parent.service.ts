import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, Observable } from 'rxjs';

import { API_CONFIG } from '../config';

@Injectable({
  providedIn: 'root'
})
export class ParentService {

  constructor(private http: HttpClient) {
  }

  getRelationships(): Observable<any> {
    return this.http.get<String[]>(`${API_CONFIG.baseUrl}/parent/relationships`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção de relacionamentos', err);
          throw err;
        })
      );
  }
}
