import { Component } from '@angular/core';
import { Teacher } from '../../../interfaces/Teacher';
import { TeacherService } from '../../../services/teacher/teacher.service';
import { forkJoin, Observable } from 'rxjs';
import { VacationTeamService } from '../../../services/vacation-team/vacation-team.service';
import { VacationTeam } from '../../../interfaces/Vacation-team';
import { Team } from '../../../interfaces/Team';

@Component({
  selector: 'app-vacation-team-list',
  templateUrl: './vacation-team-list.component.html',
  styleUrls: ['./vacation-team-list.component.css']
})
export class VacationTeamListComponent {

  originalVacationTeams!: VacationTeam[];
  vacationTeams!: VacationTeam[];
  teachers: Teacher[] = [];

  /*Table variables*/
  tableHeaders: String[] = ['Turma', 'Professor', 'Série', 'Nº de alunos', 'Sala de aula', 'Gerenciar'];
  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/vacation-team-edit', function: null},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: this.printTeam.bind(this)},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTeam.bind(this)}
  ];
  filters = [
    {name: 'ordem alfabética', function: this.sortVacationTeamsByName.bind(this)},
  ];
  filterText!: string;
  grades = [
    {
      pt: '1º Série',
      en: 'PRIMEIRA_SERIE'
    },
    {
      pt: '2º Série',
      en: 'SEGUNDA_SERIE'
    },
    {
      pt: '3º Série',
      en: 'TERCEIRA_SERIE'
    },
    {
      pt: '4º Série',
      en: 'QUARTA_SERIE'
    },
    {
      pt: '5º Série',
      en: 'QUINTA_SERIE'
    }
  ];

  constructor(private vacationTeamService: VacationTeamService, private teacherService: TeacherService) {
  }

  getGradeName(grande: string) {
    return this.grades.find(item => item.en === grande)?.pt;
  }

  ngOnInit(): void {
    this.getVacationTeams();
    this.filterText = this.filters[0].name;
  }

  getVacationTeams(): void {
    this.vacationTeamService.getVacationTeams().subscribe((vacationTeams: VacationTeam[]): void => {
      this.originalVacationTeams = vacationTeams;
      this.vacationTeams = [...this.originalVacationTeams];
      this.getTeachers(this.vacationTeams);
      this.sortVacationTeamsByName();
    });
  }

  getTeachers(vacationTeams: VacationTeam[]): void {
    const teacherObservables: Observable<Teacher>[] = vacationTeams
      .filter(vacationTeam => vacationTeam.teacherId !== null)
      .map((vacationTeam: VacationTeam) => this.teacherService.getTeacherById(vacationTeam.teacherId as number));

    forkJoin(teacherObservables).subscribe((teachers: Teacher[]): void => {
      this.teachers = teachers;
    });
  }

  getTeacherInfo(vacationTeam: VacationTeam, attribute: string): string {

    const teacher: Teacher | undefined = this.teachers.find(teacher => teacher.id === vacationTeam.teacherId);

    switch (attribute) {
      case 'fullName': {
        return teacher ? teacher.fullName : '';
      }
      case 'phoneNumber': {
        return teacher ? teacher.phoneNumber : '';
      }
      default:
        return '';
    }
  }

  deleteTeam(team: VacationTeam): void {
    const teamId: number = <number>team.id;
    let op: boolean = confirm('Deseja deletar a creche de férias?');
    if (op)
      this.vacationTeamService.deleteVacationTeam(teamId).subscribe((): void => {
        this.getVacationTeams();
      });
  }

  printTeam(vacationTeam: VacationTeam): void {

    let newWindow: Window = <Window>window.open(`/vacation-team-edit/${vacationTeam.id}`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };

  }

  filterVacationTeamList(event: Event): void {

    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.vacationTeams = this.originalVacationTeams.filter((vacationTeam: VacationTeam) => {
      const teamFullNameMatch: boolean = vacationTeam.name.toLowerCase().includes(inputValue);
      return teamFullNameMatch;
    });
  }

  sortVacationTeamsByName(): void {
    this.vacationTeams = this.originalVacationTeams.sort(function (a: VacationTeam, b: VacationTeam): number {
      let nameA: string = a.name.toLowerCase();
      let nameB: string = b.name.toLowerCase();
      if (nameA < nameB)
        return -1;
      if (nameA > nameB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortVacationTeamsByName.name);
  }

  updateBtnText(funcName: string) {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }

  printTeamList(): void {
    window.print();
  }
}
