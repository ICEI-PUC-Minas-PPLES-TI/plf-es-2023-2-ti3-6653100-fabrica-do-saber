import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { catchError, Observable, tap } from 'rxjs';

import { API_CONFIG } from '../config';
import { Team } from '../../interfaces/Team';
import { Student } from '../../interfaces/Student';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private http: HttpClient) {
  }

  createTeam(team: any): Observable<any> {
    return this.http.post<Team>(`${API_CONFIG.baseUrl}/team`, team)
      .pipe(
        tap(response => {
          console.log('Turma criada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação da turma', err);
          throw err;
        })
      );
  }

  updateTeam(id: number, team: any): Observable<any> {
    return this.http.put<Team>(`${API_CONFIG.baseUrl}/team/${id}`, team)
      .pipe(
        tap(response => {
          console.log('Turma atualizada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na atualização da turma', err);
          throw err;
        })
      );
  }

  deleteTeam(id: number): Observable<Team> {
    return this.http.delete<Team>(`${API_CONFIG.baseUrl}/team/${id}`)
      .pipe(
        tap(response => {
          console.log('Turma excluída com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na exclusão da turma', err);
          throw err;
        })
      );
  }

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(`${API_CONFIG.baseUrl}/team`);
  }

  getTeamById(id: number): Observable<Team> {
    return this.http.get<Team>(`${API_CONFIG.baseUrl}/team/${id}`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção da turma', err);
          throw err;
        })
      );
  }

  getStudents(id: number): Observable<Student[]> {
    return this.http.get<Student[]>(`${API_CONFIG.baseUrl}/team/${id}/students`);
  }

}
