import {Component, Input} from '@angular/core';
import {Student} from '../../../interfaces/Student';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  @Input() student!: Student;
  @Input() title!: string;

  constructor() {
  }

  onTeamChange(newTeam: number): void {
    this.student.team.id = parseInt(this.formatSelect(newTeam));
  }

  onHomeStateChange(newState: string): void {
    this.student.homeState = this.formatSelect(newState);
  }

  onReligionChange(newReligion: string): void {
    this.student.religion = this.formatSelect(newReligion);
  }

  onRaceChange(newRace: string): void {
    this.student.race = this.formatSelect(newRace);
  }

  formatSelect(select: any): any {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
