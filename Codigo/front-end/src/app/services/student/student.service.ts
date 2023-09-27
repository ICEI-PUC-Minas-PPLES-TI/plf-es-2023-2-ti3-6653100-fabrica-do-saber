import {Injectable} from '@angular/core';
import {Student} from '../../interfaces/Student';
import {HttpClient} from '@angular/common/http';
import {API_CONFIG} from '../config';
import {catchError, Observable, tap} from 'rxjs';
import {ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.toastr.toastrConfig.positionClass = 'toastr-center';
  }

  createStudent(student: Student): Observable<any> {
    return this.http.post<Student>(`${API_CONFIG.baseUrl}/student`, student)
      .pipe(
        tap(response => {
          console.log('Estudante criado com sucesso!', response);
          this.toastr.success('Estudante criado com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na criação do estudante', err);
          this.toastr.error('Erro na criação do estudante', 'Erro');
          throw err;
        })
      );
  }

  updateStudent(id: number, student: Student): Observable<any> {
    return this.http.put<Student>(`${API_CONFIG.baseUrl}/student/${id}`, student)
      .pipe(
        tap(response => {
          console.log('Estudante atualizado com sucesso!', response);
          this.toastr.success('Estudante atualizado com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na atualização do estudante', err);
          this.toastr.error('Erro na atualização do estudante', 'Erro');
          throw err;
        })
      );
  }

  deleteStudent(id: number) {
    return this.http.delete<Student>(`${API_CONFIG.baseUrl}/student/${id}`)
      .pipe(
        tap(response => {
          console.log('Estudante excluido com sucesso!', response);
          this.toastr.success('Estudante excluido com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na exclusão do estudante', err);
          this.toastr.error('Erro na exclusão do estudante', 'Erro');
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
