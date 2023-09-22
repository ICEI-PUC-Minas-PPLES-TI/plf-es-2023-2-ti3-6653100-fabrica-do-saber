import {Injectable} from '@angular/core';
import {Student} from "../interfaces/Student";
import {HttpClient} from "@angular/common/http";
import {API_CONFIG} from "./config";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) {
  }

  createStudent(student: any): Observable<any> {
    return this.http.post(`${API_CONFIG.baseUrl}/student`, student);
  }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${API_CONFIG.baseUrl}/student`);
  }

  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${API_CONFIG.baseUrl}/student/${id}`)
  }
}
