import {Injectable} from '@angular/core';
import {Student} from "../interfaces/Student";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {API_CONFIG} from "./config";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) {}

  getStudents(): Observable<Student[]>  {
    return this.http.get<Student[]>(`${API_CONFIG.baseUrl}/student`);
  }

  getStudentById(id: number): Observable<Student>  {
    return this.http.get<Student>(`${API_CONFIG.baseUrl}/student/${id}`)
  }
}
