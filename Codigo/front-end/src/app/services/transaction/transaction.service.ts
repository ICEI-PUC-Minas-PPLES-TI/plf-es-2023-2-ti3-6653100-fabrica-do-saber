import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Teacher} from '../../interfaces/Teacher';
import {catchError, Observable, tap} from 'rxjs';
import {API_CONFIG} from '../config';
import {Transaction} from '../../interfaces/Transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient) {
  }

  createTransaction(transaction: Transaction): Observable<any> {
    return this.http.post<Transaction>(`${API_CONFIG.baseUrl}/transaction`, transaction)
      .pipe(
        tap(response => {
          console.log('Transação criada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na criação da transação', err);
          throw err;
        })
      );
  }

  updateTransaction(id: number, transaction: Teacher): Observable<any> {
    return this.http.put<Teacher>(`${API_CONFIG.baseUrl}/transaction/${id}`, transaction)
      .pipe(
        tap(response => {
          console.log('Transação atualizada com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na atualização da transação', err);
          throw err;
        })
      );
  }

  deleteTransaction(id: number): Observable<Transaction> {
    return this.http.delete<Transaction>(`${API_CONFIG.baseUrl}/transaction/${id}`)
      .pipe(
        tap(response => {
          console.log('Transação excluída com sucesso!', response);
        }),
        catchError(err => {
          console.log('Erro na exclusão da Transação', err);
          throw err;
        })
      );
  }

  getTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${API_CONFIG.baseUrl}/transaction`);
  }

  getTransactionById(id: number): Observable<Transaction> {
    return this.http.get<Transaction>(`${API_CONFIG.baseUrl}/transaction/${id}`)
      .pipe(
        catchError(err => {
          console.log('Erro na obtenção da transação', err);
          throw err;
        })
      );
  }
}
