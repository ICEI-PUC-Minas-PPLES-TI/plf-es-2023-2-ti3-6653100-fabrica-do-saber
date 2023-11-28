import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, tap } from 'rxjs';
import { API_CONFIG } from '../config';
import { VacationTeam } from '../../interfaces/Vacation-team';
import {Student} from '../../interfaces/Student';

@Injectable({
  providedIn: 'root'
})
export class VacationTeamService {

  constructor(private http: HttpClient) {
  }

  createVacationTeam(vacationTeam: any): Observable<any> {
    return this.http.post<VacationTeam>(`${API_CONFIG.baseUrl}/vacation-team`, vacationTeam)
      .pipe(
        tap(response => {
          console.log('Creche de férias criada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação da creche de férias', err);
          throw err;
        })
      );
  }

  updateVacationTeam(id: number, vacationTeam: any): Observable<any> {
    return this.http.put<VacationTeam>(`${API_CONFIG.baseUrl}/vacation-team/${id}`, vacationTeam)
      .pipe(
        tap(response => {
          console.log('Creche de férias atualizada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na atualização da creche de férias', err);
          throw err;
        })
      );
  }

  deleteVacationTeam(id: number): Observable<VacationTeam> {
    return this.http.delete<VacationTeam>(`${API_CONFIG.baseUrl}/vacation-team/${id}`)
      .pipe(
        tap(response => {
          console.log('Creche de férias excluída com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na exclusão da creche de férias', err);
          throw err;
        })
      );
  }

  getVacationTeams(): Observable<VacationTeam[]> {
    return this.http.get<VacationTeam[]>(`${API_CONFIG.baseUrl}/vacation-team`);
  }

  getVacationTeamById(id: number): Observable<VacationTeam> {
    return this.http.get<VacationTeam>(`${API_CONFIG.baseUrl}/vacation-team/${id}`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção da creche de férias', err);
          throw err;
        })
      );
  }

  getStudents(id: number): Observable<Student[]> {
    return this.http.get<Student[]>(`${API_CONFIG.baseUrl}/vacation-team/${id}/students`);
  }
}
