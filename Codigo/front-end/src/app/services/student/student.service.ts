import { Injectable } from '@angular/core';
import { Student } from '../../interfaces/Student';
import { HttpClient } from '@angular/common/http';
import { API_CONFIG } from '../configs/config';
import { catchError, Observable, tap } from 'rxjs';

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

}
