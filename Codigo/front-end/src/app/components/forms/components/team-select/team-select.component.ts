import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TeamService } from '../../../../services/team/team.service';
import { Team } from '../../../../interfaces/Team';

@Component({
  selector: 'app-team-select',
  templateUrl: './team-select.component.html',
  styleUrls: ['./team-select.component.css']
})
export class TeamSelectComponent {

  @Input() team!: string;
  @Output() teamChange: EventEmitter<string> = new EventEmitter<string>();

  teams!: Team[];

  constructor(private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    this.teamService.getTeams().subscribe((teams: Team[]): void => {
      this.teams = teams;
    });
  }

  onTeamChange(event: any): void {
    this.team = event.target.value;
    this.teamChange.emit(this.team);
  }

}
