import { Component } from '@angular/core';

import { forkJoin, Observable } from 'rxjs';

import { Student } from '../../../interfaces/Student';
import { StudentService } from '../../../services/student/student.service';
import { Team } from '../../../interfaces/Team';
import { TeamService } from '../../../services/team/team.service';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css'],
})

export class StudentListComponent {

  originalStudents: Student[] = [];
  students: Student[] = [];
  teams: Team[] = [];

  /*Table variables*/
  tableHeaders: String[] = ['Nome', 'Idade', 'Responsável', 'Responsável', 'Turma', 'Data de registro', 'Gerenciar'];
  buttons = [
    { iconClass: 'fa fa-edit', title: 'Editar', route: '/student-edit', function: null },
    { iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: this.printStudent.bind(this) },
    { iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteStudent.bind(this) }
  ];
  filters = [
    { name: 'ordem alfabética', function: this.sortStudentsByName.bind(this) },
    { name: 'id', function: this.sortStudentsById.bind(this) }
  ];
  filterText!: string;

  constructor(private studentService: StudentService, private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.getStudents();
    this.filterText = this.filters[0].name;
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.originalStudents = students;
      this.students = [...this.originalStudents];
      this.getTeams(this.students);
      this.sortStudentsByName();
    });
  }

  getTeams(students: Student[]): void {
    const teamObservables: Observable<Team>[] = students.map((student: Student): Observable<Team> => {
      return this.teamService.getTeamById(student.team.id);
    });

    forkJoin(teamObservables).subscribe((teams: Team[]): void => {
      this.teams = teams;
    });
  }

  getTeamName(student: Student): string {
    const team: Team | undefined = this.teams.find(team => team.id === student.team.id);
    return team ? team.name : '';
  }

  deleteStudent(student: Student): void {
    let op: boolean = confirm('Deseja deletar o aluno?');
    if (op)
      this.studentService.deleteStudent(student.id).subscribe((): void => {
        this.getStudents();
      });
  }

  printStudentList(): void {
    window.print();
  }

  printStudent(student: Student): void {

    let newWindow: Window = <Window>window.open(`/student-edit/${student.id}`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }

  filterStudentList(event: Event): void {

    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.students = this.originalStudents.filter((student: Student) => {

      const studentFullNameMatch: boolean = student.fullName.toLowerCase().includes(inputValue);
      const parent00FullNameMatch: boolean = student.parents[0].fullName.toLowerCase().includes(inputValue);
      const parent01FullNameMatch: boolean = student.parents[1].fullName.toLowerCase().includes(inputValue);

      return studentFullNameMatch || parent00FullNameMatch || parent01FullNameMatch;
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
