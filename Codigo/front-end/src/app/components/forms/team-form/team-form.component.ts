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

  onselectedStudentIdsChange(idArr: number[]): void {
    this.selectedStudentIds = idArr;
    this.getStudents();
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.selectedStudents = students
        .filter(student => this.selectedStudentIds.includes(student.id))
        .map(student => student.fullName);
    });
  }
}
