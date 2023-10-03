import {Component, Input} from '@angular/core';
import {Team} from '../../../interfaces/Team';
import {Student} from '../../../interfaces/Student';
import {StudentService} from '../../../services/student/student.service';

@Component({
  selector: 'app-team-form',
  templateUrl: './team-form.component.html',
  styleUrls: ['./team-form.component.css']
})
export class TeamFormComponent {

  @Input() team!: Team;
  @Input() title!: string;

  selectedStudentIds!: number[];
  selectedStudents !: string[];

  constructor(private studentService: StudentService) {
  }

  onSelectedStudentIdsChange(idArr: number[]): void {
    this.selectedStudentIds = idArr;
    this.getStudents();
  }

  onTeacherChange(teacherId: number):void {
    this.team.teacherId = parseInt(this.formatSelect(teacherId.toString()));
  }

  onGrandeChange(grade: string):void {
    this.team.grade = this.formatSelect(grade);
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
