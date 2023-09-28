import { Component, Input } from '@angular/core';
import { Team } from '../../../interfaces/Team';
import { ConsoleLogger } from '@angular/compiler-cli';

@Component({
  selector: 'app-team-form',
  templateUrl: './team-form.component.html',
  styleUrls: ['./team-form.component.css']
})
export class TeamFormComponent {

  @Input() team!: Team;
  @Input() title!: string;

  constructor() {
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
