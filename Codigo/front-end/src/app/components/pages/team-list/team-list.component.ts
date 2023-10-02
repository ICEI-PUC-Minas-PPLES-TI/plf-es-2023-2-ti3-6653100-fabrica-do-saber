import {Component} from '@angular/core';
import {Team} from '../../../interfaces/Team';
import {TeamService} from '../../../services/team/team.service';
import {TeacherService} from '../../../services/teacher/teacher.service';
import {Teacher} from '../../../interfaces/Teacher';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css'],
})

export class TeamListComponent {

  /*TeamImp variables*/
  originalTeams: Team[] = [];
  teams: Team[] = [];
  teachers!: Teacher[];

  /*Table variables*/
  tableHeaders: String[] = ['Turma', 'Professor', 'Série', 'Nº de alunos', 'Gerenciar'];
  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/team-edit', function: null},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: null},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTeam.bind(this)}
  ];
  filters = [
    {name: 'ordem alfabética', function: this.sortTeamsByName.bind(this)},
    {name: 'id', function: this.sortTeamsById.bind(this)}
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
      this.sortTeamsByName();
    });
  }

  deleteTeam(id: number): void {
    this.teamService.deleteTeam(id).subscribe((): void => {
      this.getTeams();
    });
  }

  getTeacherName(id: number) {

    return this.teacherService.getTeacherById(id);
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

  sortTeamsById(): void {
    this.teams = this.originalTeams.sort(function (a: Team, b: Team): number {
      let idA: number = a.id;
      let idB: number = b.id;
      if (idA < idB)
        return -1;
      if (idA > idB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTeamsById.name);
  }

  updateBtnText(funcName: string) {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }

}
