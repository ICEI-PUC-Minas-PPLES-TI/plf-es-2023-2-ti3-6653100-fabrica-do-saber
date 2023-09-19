import { Injectable } from '@angular/core';
import {Student} from "../interfaces/Student";

@Injectable({
  providedIn: 'root'
})
export class StudentService {

private students: Student[] = [
  {
    id: 5,
    name: 'Josefina da Silva',
    contactNumber: 31984173698,
    contactName: 'Paulinho',
    age: 5,
    birthDate: '20/04/2018',
    class: 'Turma A',
    status: 'Ativo'
  },
  {
    id: 4,
    name: 'Joao Pereira',
    contactNumber: 31923198688,
    contactName: 'Maria',
    age: 4,
    birthDate: '28/10/2020',
    class: 'Turma B',
    status: 'Ativo'
  },
  {
    id: 3,
    name: 'Carlitos da Silva',
    contactNumber: 31923198688,
    contactName: 'Maria',
    age: 5,
    birthDate: '28/10/2020',
    class: 'Turma B',
    status: 'Ativo'
  },
  {
    id: 2,
    name: 'Juanito Jones',
    contactNumber: 31923198688,
    contactName: 'Maria',
    age: 5,
    birthDate: '28/10/2020',
    class: 'Turma B',
    status: 'Ativo'
  },
];

  constructor() { }

  getStudents(): Student[] {
    return [...this.students];
  }

  getStudentById(id: number): Student[] {
    return this.students.filter(student => student.id === id);
  }
}
