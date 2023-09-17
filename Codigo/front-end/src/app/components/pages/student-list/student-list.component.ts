import {Component} from '@angular/core';
import {Student} from '../../../interfaces/Student';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent {

  originalStudents: Student[] = [
    {
      name: 'Josefina da Silva',
      contactNumber: 31984173698,
      contactName: 'Paulinho',
      age: 5,
      birthDate: '20/04/2018',
      class: 'Turma A',
      status: 'Ativo'
    },
    {
      name: 'Joao da Silva',
      contactNumber: 31923198688,
      contactName: 'Maria',
      age: 4,
      birthDate: '28/10/2020',
      class: 'Turma B',
      status: 'Ativo'
    },
    {
      name: 'Carlitos da Silva',
      contactNumber: 31923198688,
      contactName: 'Maria',
      age: 3,
      birthDate: '28/10/2020',
      class: 'Turma B',
      status: 'Ativo'
    },
  ];
  students = [...this.originalStudents];

  filterStudentList(event: Event): void {
    const searchInput = event.target as HTMLInputElement;
    const inputValue = searchInput.value.toLowerCase();
    this.students = this.originalStudents.filter((student: Student) => student.name.toLowerCase().includes(inputValue));
  }

}
