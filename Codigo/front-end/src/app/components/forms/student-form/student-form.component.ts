import {Component, Input, OnInit} from '@angular/core';
import {Student} from '../../../interfaces/Student';
import {Team} from '../../../interfaces/Team';
import {StudentService} from '../../../services/student/student.service';
import {TeamService} from '../../../services/team/team.service';
import {VacationTeam} from '../../../interfaces/Vacation-team';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {

  @Input() student!: Student;
  @Input() title!: string;
  studentTeamId!: number;

  constructor(private studentService: StudentService, private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.getTeam();
  }

  onTeamChange(newTeam: number): void {
    this.updateTeam(parseInt(this.formatSelect(newTeam)));
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

  getTeam(): void {
    this.studentService.getActiveTeam(this.student.id).subscribe((team: Team): void => {
      if (team.id)
        this.studentTeamId = team.id;
    });
  }

  updateTeam(newTeam: number): void {
    if (this.student.teamIds.length == 0)
      this.student.teamIds.push(newTeam);

    if (this.student.teamIds.length == 1) {
      this.studentService.getActiveVacationTeams(this.student.id).subscribe((vacationTeam: VacationTeam[]) => {
        console.log(this.student.teamIds[0] === vacationTeam[0].id);
        if (vacationTeam[0].id === this.student.teamIds[0])
          this.student.teamIds.push(newTeam);
      });
    }

    if (this.student.teamIds.length > 1) {
      this.studentService.getAllTeams(this.student.id).subscribe((teams: Team[]): void => {
        this.studentService.getActiveTeam(this.student.id).subscribe((team: Team): void => {
          teams.forEach((t: Team): void => {
            if (t.id)
              if (t.id === team.id) {
                this.student.teamIds.splice(this.student.teamIds.indexOf(t.id), 1);
                this.student.teamIds.push(newTeam);
              }
          });
        });
      });
    }
  }

  formatSelect(select: any): any {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
