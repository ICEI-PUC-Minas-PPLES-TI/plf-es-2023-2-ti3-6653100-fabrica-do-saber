import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
// import {TeamService} from '../../../services/team/team.service';
import { Team } from "../../../interfaces/Team";
import {TeamImp} from '../../../classes/team/team-imp';

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.css']
})
export class TeamCreateComponent {

  team: Team = new TeamImp();

  constructor(private router: Router/*, private teamService: TeamService*/) {
  }

  createTeam(): void {
    console.log(this.team);
    // this.teamService.createTeam(this.team).subscribe();
  }

  cancel(): void {
    // this.toastr.error('Ação cancelada');
    // this.router.navigate(['/team-list']);
  }

}
