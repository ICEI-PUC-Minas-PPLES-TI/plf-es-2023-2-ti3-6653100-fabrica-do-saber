import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Student} from 'src/app/interfaces/Student';
import {StudentService} from '../../../../services/student/student.service';

@Component({
  selector: 'app-student-select',
  templateUrl: './student-select.component.html',
  styleUrls: ['./student-select.component.css']
})
export class StudentSelectComponent {

  @Input() selectedStudentIds: number[] = [];
  @Output() selectedStudentIdsChange: EventEmitter<number[]> = new EventEmitter<number[]>();

  students!: Student[];

  constructor(private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.getStudents();
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.students = students;
    });
  }

  addStudents():void {
    this.selectedStudentIdsChange.emit(this.selectedStudentIds);
  }

}
