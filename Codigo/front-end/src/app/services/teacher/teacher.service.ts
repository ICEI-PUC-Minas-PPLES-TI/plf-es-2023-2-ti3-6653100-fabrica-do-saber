import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, tap} from 'rxjs';
import {API_CONFIG} from '../configs/config';
import {Teacher} from '../../interfaces/Teacher';

@Injectable({
  providedIn: 'root'
})
export class TeacherService {

  constructor(private http: HttpClient) {
  }

  createTeacher(teacher: Teacher): Observable<any> {
    return this.http.post<Teacher>(`${API_CONFIG.baseUrl}/teacher`, teacher)
      .pipe(
        tap(response => {
          console.log('Professor criado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação do professor', err);
          throw err;
        })
      );
  }

  updateTeacher(id: number, teacher: Teacher): Observable<any> {
    return this.http.put<Teacher>(`${API_CONFIG.baseUrl}/teacher/${id}`, teacher)
      .pipe(
        tap(response => {
          console.log('Professor atualizado com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na atualização do professor', err);
          throw err;
        })
      );
  }

  deleteTeacher(id: number): Observable<Teacher> {
    return this.http.delete<Teacher>(`${API_CONFIG.baseUrl}/teacher/${id}`)
      .pipe(
        tap(response => {
          console.log('Professor excluído com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na exclusão do professor', err);
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

  formatCurrency(currency: any): number {

    const currencyValueStr: string = currency.toString().replace('R$', '');
    const cleanedValue: string = currencyValueStr.replace(/\./g, '').replace(',', '.');

    return typeof currency === 'number' ? currency : parseFloat((parseFloat(cleanedValue) * 10).toFixed(2));
  }


}
