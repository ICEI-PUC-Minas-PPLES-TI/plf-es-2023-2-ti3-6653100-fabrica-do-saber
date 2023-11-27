import { Component, Input } from '@angular/core';
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

  onAcademicFormationStatus(newStatus: string): void {
    this.teacher.academicFormationStatus = this.formatSelect(newStatus);
  }

  formatStr(str: string) {
    return str
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toUpperCase()
      .replace(/\s+/g, '_');
  }

  formatSelect(select: string): string {
    const parts: string[] = select.split(':');
    return parts[1].trim();
  }
}
