import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { catchError, Observable, tap } from 'rxjs';

import { API_CONFIG } from '../config';
import { Student } from '../../interfaces/Student';
import { Team } from '../../interfaces/Team';
import { VacationTeam } from '../../interfaces/Vacation-team';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) {
  }

  createStudent(student: Student): Observable<any> {
    return this.http.post<Student>(`${API_CONFIG.baseUrl}/student`, student)
      .pipe(
        tap(response => {
          console.log('Estudante criado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação do estudante', err);
          throw err;
        })
      );
  }

  updateStudent(id: number, student: Student): Observable<any> {
    return this.http.put<Student>(`${API_CONFIG.baseUrl}/student/${id}`, student)
      .pipe(
        tap(response => {
          console.log('Estudante atualizado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na atualização do estudante', err);
          throw err;
        })
      );
  }

  deleteStudent(id: number): Observable<Student> {
    return this.http.delete<Student>(`${API_CONFIG.baseUrl}/student/${id}`)
      .pipe(
        tap(response => {
          console.log('Estudante excluído com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na exclusão do estudante', err);
          throw err;
        })
      );
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${API_CONFIG.baseUrl}/student`);
  }

  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${API_CONFIG.baseUrl}/student/${id}`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção do estudante', err);
          throw err;
        })
      );
  }

  getAllTeams(id:number): Observable<Team[]> {
    return this.http.get<Team[]>(`${API_CONFIG.baseUrl}/student/${id}/active-all-teams`);
  }

  getActiveTeam(id: number): Observable<Team> {
    return this.http.get<Team>(`${API_CONFIG.baseUrl}/student/${id}/active-team`);
  }

  getActiveVacationTeams(id: number): Observable<VacationTeam[]> {
    return this.http.get<VacationTeam[]>(`${API_CONFIG.baseUrl}/student/${id}/active-vacation-teams`);
  }

  getVacationTeams(id: number): Observable<VacationTeam[]> {
    return this.http.get<VacationTeam[]>(`${API_CONFIG.baseUrl}/student/${id}/vacation-team`);
  }

}
