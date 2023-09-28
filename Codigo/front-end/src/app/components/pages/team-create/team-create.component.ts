import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
// import {TeamService} from '../../../services/team/team.service';
import { Team } from "../../../interfaces/Team";

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.css']
})
export class TeamCreateComponent {

  team: Team = {

    id: 0,
    name: "",
    numberOfStudents: 0,
    grade: "",
    classRoom: "",
    students: [
      {
        parents: [
          {
            fullName: 'jh',
            cpf: 'jh',
            rg: 'jh',
            email: 'jh',
            occupation: 'jh',
            company: 'jh',
            phoneNumber: 'jh',
            streetAddress: 'jh',
            addressNumber: 'jh',
            addressComplement: 'jh',
            neighborhood: 'jh',
            cityOfResidence: 'jh',
            zipCode: 'jh',
            homeState: 'jh',
            birthDate: 'jh',
            relationship: 'jh'
          },
          {
            fullName: 'jh',
            cpf: 'jh',
            rg: 'jh',
            email: 'jh',
            occupation: 'jh',
            company: 'jh',
            phoneNumber: 'jh',
            streetAddress: 'jh',
            addressNumber: 'jh',
            addressComplement: 'jh',
            neighborhood: 'jh',
            cityOfResidence: 'jh',
            zipCode: 'jh',
            homeState: 'jh',
            birthDate: 'jh',
            relationship: 'jh'
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

      id: 0,
      fullName: '',
      cpf: '',
      rg: '',
      email: '',
      phoneNumber: '',
      addressNumber: '',
      addressComplement: '',
      streetAddress: '',
      neighborhood: '',
      zipCode: '',
      cityOfResidence: '',
      homeState: '',
      registrationDate: '',
      birthDate: '',

      salary: '',
      hireDate: '',
      terminationDate: ''
    },
  };

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

  // constructor(private router: Router, private toastr: ToastrService, private teamService: TeamService) {
  // }

  createTeam(): void {
    // console.log(this.team);
    // this.teamService.createTeam(this.team).subscribe();
  }

  cancel(): void {
    // this.toastr.error('Ação cancelada');
    // this.router.navigate(['/team-list']);
  }

}
