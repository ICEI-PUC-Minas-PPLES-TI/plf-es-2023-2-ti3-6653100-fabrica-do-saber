import { Component, Input } from '@angular/core';
import { Team } from '../../../interfaces/Team';

@Component({
  selector: 'app-team-form',
  templateUrl: './team-form.component.html',
  styleUrls: ['./team-form.component.css']
})
export class TeamFormComponent {

  @Input() team!: Team;
  @Input() title!: string;

  teamStudentIds: number[] = [];

  constructor() {
  }

  onStudentChange(selectedStudentIds: number[]): void {
    // Handle the selected student IDs here
    // You can access the selectedStudentIds array
    console.log(selectedStudentIds);

    // If you want to update team.grade based on selected students
    // You might want to fetch student information based on selectedStudentIds
    // and then update team.grade accordingly.

    

    // onStudentChange(newStudent: string): void {
    //   this.team.grade = this.formatSelect(newStudent);
    // }
  
    // formatSelect(select: string): string {
    //   const parts: string[] = select.split(':');
    //   return parts[1].trim();
    // }
  }
}
