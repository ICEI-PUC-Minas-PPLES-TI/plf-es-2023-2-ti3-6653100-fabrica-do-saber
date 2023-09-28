import { Component } from '@angular/core';
import { Team } from '../../../interfaces/Team';
// import { TeamService } from '../../../services/team/team.service';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css'],
})

export class TeamListComponent {

  /*TeamImp variables*/
  originalTeams: Team[] = [];

  teams: Team[] = [
    {
      id: 1,
  name: "Team A",
  numberOfStudents: 20,
  grade: "5th Grade",
  classRoom: "Room 101",
  students: [
    {
      parents: [
        {
          fullName: "John Doe",
          cpf: "123.456.789-00",
          rg: "987654321",
          email: "johndoe@example.com",
          occupation: "Engineer",
          company: "ABC Corporation",
          phoneNumber: "(123) 456-7890",
          streetAddress: "123 Main Street",
          addressNumber: "Apt 4B",
          addressComplement: 'jh',
          neighborhood: "Suburbia",
          cityOfResidence: "Metropolis",
          zipCode: "12345",
          homeState: "ST",
          birthDate: "01/15/1980",
          relationship: "Parent",
        },
        {
          fullName: "Jane Doe",
          cpf: "987.654.321-00",
          rg: "123456789",
          email: "janedoe@example.com",
          occupation: "Doctor",
          company: "XYZ Hospital",
          phoneNumber: "(987) 654-3210",
          streetAddress: "456 Elm Street",
          addressNumber: "Suite 3C",
          addressComplement: 'jh',
          neighborhood: "Downtown",
          cityOfResidence: "Metropolis",
          zipCode: "54321",
          homeState: "ST",
          birthDate: "02/20/1975",
          relationship: "Parent",
        },
      ],
      id: 1,
      fullName: "Alice Johnson",
      registrationDate: "09/27/2023",
      birthDate: "03/10/2005",
      hometown: "Smallville",
      homeState: "ST",
      nationality: "American",
      religion: "Christian",
      race: "White",
      streetAddress: "789 Oak Avenue",
      addressNumber: "Unit 2D",
      addressComplement: 'jh',
      neighborhood: "Suburbia",
      cityOfResidence: "Metropolis",
      zipCode: "67890",
    },
    // Add more student objects if needed
  ],
  teacher: {
    id: 101,
  fullName: "Mrs. Smith",
  cpf: "123.456.789-00",
  rg: "987654321",
  email: "mrssmith@example.com",
  phoneNumber: "(555) 123-4567",
  addressNumber: "Unit 3B",
  addressComplement: "Building C",
  streetAddress: "789 Elm Street",
  neighborhood: "Downtown",
  zipCode: "54321",
  cityOfResidence: "Metropolis",
  homeState: "ST",
  registrationDate: "09/15/2010",
  birthDate: "05/20/1980",
  salary: "$50,000",
  hireDate: "08/01/2010",
  terminationDate: "N/A",
  },
    }
  ];

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

  // constructor(private teamService: TeamService) {
  // }

  ngOnInit(): void {
    this.getTeams();
    this.filterText = this.filters[0].name;
  }

  getTeams(): void {
    // this.teamService.getTeams().subscribe((teams: Team[]): void => {
    //   this.originalTeams = teams;
    //   this.teams = [...this.originalTeams];
    //   this.sortTeamsByName();
    // });
  }

  deleteTeam(id: number): void {
    // this.teamService.deleteTeam(id).subscribe((): void => {
    //   this.getTeams();
    // });
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
