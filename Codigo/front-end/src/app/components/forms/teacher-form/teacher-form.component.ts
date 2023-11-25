import {Component, Input} from '@angular/core';
import { Teacher } from '../../../interfaces/Teacher';

@Component({
  selector: 'app-teacher-form',
  templateUrl: './teacher-form.component.html',
  styleUrls: ['./teacher-form.component.css']
})
export class TeacherFormComponent {

  @Input() teacher!: Teacher;
  @Input() title!: string;

  constructor() {
  }

  onHomeStateChange(newState: string): void {
    this.teacher.homeState = this.formatSelect(newState);
  }

  onaAcademicFormationStatus(newStatus: string): void {
    this.teacher.academicFormationStatus = this.formatSelect(newStatus);
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
