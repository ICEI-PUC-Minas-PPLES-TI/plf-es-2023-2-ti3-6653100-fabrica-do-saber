import {Component} from '@angular/core';
import {Team} from '../../../interfaces/Team';
import {ActivatedRoute, Router} from '@angular/router';
import {TeamService} from '../../../services/team/team.service';
import {catchError, tap} from 'rxjs';

@Component({
  selector: 'app-team-edit',
  templateUrl: './team-edit.component.html',
  styleUrls: ['./team-edit.component.css']
})
export class TeamEditComponent {

  team !: Team;
  teamId: number = 0;

  constructor(private route: ActivatedRoute, private teamService: TeamService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params): void => {
      this.teamId = parseInt(<string>params.get('id'));
      this.getTeamById(this.teamId);
    });
  }

  getTeamById(id: number): void {
    this.teamService.getTeamById(id).subscribe((team: Team): void => {
      this.team = team;
    });
  }

  updateTeam(): void {
    const formattedTeam = this.formatToRequest(this.team);
    let op: boolean = confirm('Deseja atualizar a turma?');
    if (op) {
      this.teamService.updateTeam(this.teamId, formattedTeam)
        .pipe(
          tap((response): void => {
            this.router.navigate(['/team-list']);
          }),
          catchError(err => {
            throw err;
          }))
        .subscribe();
      this.router.navigate(['/team-list']);
    }
  }

  cancel(): void {
    this.router.navigate(['/team-list']);
  }

  formatToRequest(team: Team) {
    console.log(team.studentIds);

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
