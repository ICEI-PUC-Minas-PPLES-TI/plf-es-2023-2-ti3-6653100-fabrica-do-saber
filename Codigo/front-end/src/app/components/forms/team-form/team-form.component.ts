import {Component, Input} from '@angular/core';
import {Team} from '../../../interfaces/Team';

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

  onStudentChange(newStudent: string): void {
    this.team.grade = this.formatSelect(newStudent);
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
