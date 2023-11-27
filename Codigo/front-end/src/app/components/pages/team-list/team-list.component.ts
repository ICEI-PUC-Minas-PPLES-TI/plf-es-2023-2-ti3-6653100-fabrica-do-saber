import { Component } from '@angular/core';

import { forkJoin, Observable } from 'rxjs';

import { Team } from '../../../interfaces/Team';
import { TeamService } from '../../../services/team/team.service';
import { TeacherService } from '../../../services/teacher/teacher.service';
import { Teacher } from '../../../interfaces/Teacher';
import { SelectValue } from '../../../interfaces/SelectValue';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css'],
})

export class TeamListComponent {

  originalTeams!: Team[];
  teams!: Team[];
  teachers: Teacher[] = [];
  grades: SelectValue[] = [
    {name: '1° Série', value: 'PRIMEIRA_SERIE'},
    {name: '2° Série', value: 'SEGUNDA_SERIE'},
    {name: '3° Série', value: 'TERCEIRA_SERIE'},
    {name: '4° Série', value: 'QUARTA_SERIE'},
    {name: '5° Série', value: 'QUINTA_SERIE'}
  ];

  /*Table variables*/
  tableHeaders: String[] = ['Turma', 'Professor', 'Série', 'Nº de alunos', 'Sala de aula', 'Gerenciar'];
  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/team-edit', function: null},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: this.printTeam.bind(this)},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTeam.bind(this)}
  ];
  filters = [
    {name: 'ordem alfabética', function: this.sortTeamsByName.bind(this)},
  ];
  filterText!: string;

  constructor(private teamService: TeamService, private teacherService: TeacherService) {
  }

  ngOnInit(): void {
    this.getTeams();
    this.filterText = this.filters[0].name;
  }

  getTeams(): void {
    this.teamService.getTeams().subscribe((teams: Team[]): void => {
      this.originalTeams = teams;
      this.teams = [...this.originalTeams];
      this.getTeachers(this.teams);
      this.sortTeamsByName();
    });
  }

  getTeachers(teams: Team[]): void {
    const teacherObservables: Observable<Teacher>[] = teams.map((team: Team) =>
      this.teacherService.getTeacherById(team.teacherId)
    );
    forkJoin(teacherObservables).subscribe((teachers: Teacher[]): void => {
      this.teachers = teachers;
    });
  }

  getGradeName(value: string): string | undefined {
    return this.grades.find((item: SelectValue): boolean => item.value === value)?.name;
  }

  getTeacherInfo(team: Team, attribute: string): string {
    const teacher: Teacher | undefined = this.teachers.find((teacher: Teacher): boolean => teacher.id === team.teacherId);

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

  deleteTeam(team: Team): void {
    const teamId: number = <number>team.id;
    let op: boolean = confirm('Deseja deletar a turma?');
    if (op)
      this.teamService.deleteTeam(teamId).subscribe((): void => {
        this.getTeams();
      });
  }

  printTeam(team: Team): void {

    let newWindow: Window = <Window>window.open(`/team-edit/${team.id}`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };

  }

  filterTeamList(event: Event): void {
    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.teams = this.originalTeams.filter((team: Team) => {
      const teamFullNameMatch: boolean = team.name.toLowerCase().includes(inputValue);
      return teamFullNameMatch;
    });
  }

  sortTeamsByName(): void {
    this.teams = this.originalTeams.sort(function (a: Team, b: Team): number {
      let nameA: string = a.name.toLowerCase();
      let nameB: string = b.name.toLowerCase();
      if (nameA < nameB)
        return -1;
      if (nameA > nameB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTeamsByName.name);
  }

  updateBtnText(funcName: string): void {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }

  printTeamList(): void {
    window.print();
  }
}
