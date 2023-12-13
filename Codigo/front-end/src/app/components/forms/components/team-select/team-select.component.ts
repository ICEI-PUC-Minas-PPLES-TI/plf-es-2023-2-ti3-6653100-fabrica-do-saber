import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TeamService } from '../../../../services/team/team.service';
import { Team } from '../../../../interfaces/Team';

@Component({
  selector: 'app-team-select',
  templateUrl: './team-select.component.html',
  styleUrls: ['./team-select.component.css']
})
export class TeamSelectComponent {

  @Input() teamId!: number;
  @Output() teamChange: EventEmitter<number> = new EventEmitter<number>();

  teams!: Team[];

  constructor(private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    const teamsArr: Team[] = [];
    this.teamService.getTeams().subscribe((teams: Team[]): void => {
      teams.forEach((t: Team): void => {
        if (t.type === 'team')
          teamsArr.push(t);
      });
      this.teams = teamsArr;
    });
  }

  onTeamChange(event: any): void {
    this.teamId = event.target.value;
    this.teamChange.emit(this.teamId);
  }

}
