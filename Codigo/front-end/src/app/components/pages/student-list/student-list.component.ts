import {Component} from '@angular/core';
import {Student} from '../../../interfaces/Student';
import {StudentService} from "../../../services/student.service";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent {

  studentStatus: string[] = ['Ativo', 'Desativado', '...'];
  originalStudents: Student[] = [];
  students: Student[] = [...this.originalStudents];
  filters = [
    {
      name: 'ordem alfabética',
      function: this.sortStudentsByName.bind(this)
    },
    {
      name: 'id',
      function: this.sortStudentsById.bind(this)
    }
  ];
  filterText: string = '';

  constructor(private studentService: StudentService) {
    this.filterText = this.filters[0].name;
    this.originalStudents = this.studentService.getStudents();
    this.sortStudentsByName();
  }

  filterStudentList(event: Event): void {
    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();
    this.students = this.originalStudents.filter((student: Student) => student.name.toLowerCase().includes(inputValue));
  }

  sortStudentsByName(): void {
    this.students = this.originalStudents.sort(function (a: Student, b: Student): number {
      let nameA: string = a.name.toLowerCase();
      let nameB: string = b.name.toLowerCase();
      if (nameA < nameB)
        return -1;
      if (nameA > nameB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortStudentsByName.name);
  }

  sortStudentsById(): void {
    this.students = this.originalStudents.sort(function (a: Student, b: Student): number {
      let idA: number = a.id;
      let idB: number = b.id;
      if (idA < idB)
        return -1;
      if (idA > idB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortStudentsById.name);
  }

  updateBtnText(funcName: string) {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }

}
