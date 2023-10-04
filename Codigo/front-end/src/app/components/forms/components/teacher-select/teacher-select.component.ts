import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Teacher} from '../../../../interfaces/Teacher';
import {TeacherService} from '../../../../services/teacher/teacher.service';

@Component({
  selector: 'app-teacher-select',
  templateUrl: './teacher-select.component.html',
  styleUrls: ['./teacher-select.component.css']
})
export class TeacherSelectComponent {

  @Input() teacherId!: number;
  @Output() teacherChange: EventEmitter<number> = new EventEmitter<number>();

  teachers!: Teacher[];

  constructor(private teacherService: TeacherService) {
  }

  ngOnInit(): void {
    this.getTeachers();
  }

  getTeachers(): void {
    this.teacherService.getTeachers().subscribe((teachers: Teacher[]): void => {
      this.teachers = teachers;
    });
  }

  onTeacherChange(event: any): void {
    this.teacherId = event.target.value;
    this.teacherChange.emit(this.teacherId);
  }

}
