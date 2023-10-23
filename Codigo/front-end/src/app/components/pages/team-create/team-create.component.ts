import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {TeamService} from '../../../services/team/team.service';
import {Team} from '../../../interfaces/Team';
import {TeamImp} from '../../../classes/team/team-imp';

@Component({
  selector: 'app-team-create',
  templateUrl: './team-create.component.html',
  styleUrls: ['./team-create.component.css']
})
export class TeamCreateComponent {

  team: Team = new TeamImp();

  constructor(private router: Router, private teamService: TeamService) {
  }

  createTeam(): void {
    const formattedTeam = this.formatToRequest(this.team);
    this.teamService.createTeam(formattedTeam).subscribe();
  }

  cancel(): void {
    this.router.navigate(['/team-list']);
  }

  formatToRequest(team: Team) {
    return {
      name: team.name,
      grade: team.grade,
      classroom: team.classroom,
      teacher: {
        id: team.teacherId
      },
      students: team.studentIds.map((studentId: number): { id: number } => ({id: studentId}))
    };
  }

}
