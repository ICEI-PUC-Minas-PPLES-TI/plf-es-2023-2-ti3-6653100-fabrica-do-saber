import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import {catchError, Observable, tap} from 'rxjs';
import {API_CONFIG} from '../config';
import {Teacher} from '../../interfaces/Teacher';
import {Student} from '../../interfaces/Student';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.toastr.toastrConfig.positionClass = 'toastr-center';
  }

  createTeacher(teacher: Teacher): Observable<any> {
    return this.http.post<Teacher>(`${API_CONFIG.baseUrl}/teacher`, teacher)
      .pipe(
        tap(response => {
          console.log('Professor criado com sucesso!', response);
          this.toastr.success('Professor criado com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na criação do professor', err);
          this.toastr.error('Erro na criação do professor', 'Erro');
          throw err;
        })
      );
  }

  updateTeacher(id: number, teacher: Teacher): Observable<any> {
    return this.http.put<Teacher>(`${API_CONFIG.baseUrl}/teacher/${id}`, teacher)
      .pipe(
        tap(response => {
          console.log('Professor atualizado com sucesso!', response);
          this.toastr.success('Professor atualizado com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na atualização do professor', err);
          this.toastr.error('Erro na atualização do professor', 'Erro');
          throw err;
        })
      );
  }

  deleteTeacher(id: number): Observable<Teacher> {
    return this.http.delete<Teacher>(`${API_CONFIG.baseUrl}/teacher/${id}`)
      .pipe(
        tap(response => {
          console.log('Professor excluido com sucesso!', response);
          this.toastr.success('Professor excluido com sucesso!', 'Sucesso');
        }),
        catchError(err => {
          console.log('Erro na exclusão do professor', err);
          this.toastr.error('Erro na exclusão do professor', 'Erro');
          throw err;
        })
      );
  }

  getTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(`${API_CONFIG.baseUrl}/teacher`);
  }

  getTeacherById(id: number): Observable<Teacher> {
    return this.http.get<Teacher>(`${API_CONFIG.baseUrl}/teacher/${id}`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção do professor', err);
          throw err;
        })
      );
  }

  formatCurrency(currency: string): string {

    const currencyValueStr: string = currency.split('R$')[1];
    const currencyValueNum: number = parseFloat(currencyValueStr.replace(',', '.')) * 10;

    return currencyValueNum.toFixed(2);
  }

}
