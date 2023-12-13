import { Component, Input } from '@angular/core';
import { StudentService } from '../../../services/student/student.service';
import { Student } from '../../../interfaces/Student';
import { VacationTeam } from '../../../interfaces/Vacation-team';

@Component({
  selector: 'app-vacation-team-form',
  templateUrl: './vacation-team-form.component.html',
  styleUrls: ['./vacation-team-form.component.css']
})
export class VacationTeamFormComponent {

  @Input() vacationTeam!: VacationTeam;
  @Input() title!: string;

  selectedStudentIds!: number[];
  selectedStudents !: string[];

  constructor(private studentService: StudentService) {
  }

  onSelectedStudentIdsChange(idArr: number[]): void {
    this.selectedStudentIds = idArr;
    this.getStudents();
  }

  onTeacherChange(teacherId: number): void {
    this.vacationTeam.teacherId = parseInt(this.formatSelect(teacherId.toString()));
  }

  onGrandeChange(grade: string): void {
    this.vacationTeam.grade = this.formatSelect(grade);
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.selectedStudents = students
        .filter(student => this.selectedStudentIds.includes(student.id))
        .map(student => student.fullName);
    });
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }

}
