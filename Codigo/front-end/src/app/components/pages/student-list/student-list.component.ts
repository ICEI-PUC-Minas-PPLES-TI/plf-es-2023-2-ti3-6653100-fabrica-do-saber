import {Component} from '@angular/core';
import {Student} from '../../../interfaces/Student';
import {StudentService} from "../../../services/student.service";

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css'],
})

export class StudentListComponent {

  originalStudents: Student[] = [];
  students: Student[] = [];

  // Table variables
  tableHeaders = ['Nome', 'Idade', 'Pai', 'Mãe', 'Turma', 'Data de registro', 'Gerenciar'];
  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/student-edit'},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: '#'},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: '#'}
  ];
  filters = [
    {name: 'ordem alfabética', function: this.sortStudentsByName.bind(this)},
    {name: 'id', function: this.sortStudentsById.bind(this)}
  ];
  filterText!: string;

  constructor(private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.getStudents();
    this.filterText = this.filters[0].name;
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.originalStudents = students;
      this.students = [...this.originalStudents];
      this.sortStudentsByName();
    });
  }

  filterStudentList(event: Event): void {
    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.students = this.originalStudents.filter((student: Student) => {
      const studentFullNameMatch: boolean = student.fullName.toLowerCase().includes(inputValue);
      const guardianFullNameMatch: boolean = student.guardians[0].fullName.toLowerCase().includes(inputValue);

      return studentFullNameMatch || guardianFullNameMatch;
    });
  }

  sortStudentsByName(): void {
    this.students = this.originalStudents.sort(function (a: Student, b: Student): number {
      let nameA: string = a.fullName.toLowerCase();
      let nameB: string = b.fullName.toLowerCase();
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
